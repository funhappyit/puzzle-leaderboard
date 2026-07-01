package com.example.leaderboard.domain.score.service

import com.example.leaderboard.domain.score.dto.ScoreResponse
import com.example.leaderboard.domain.score.dto.ScoreSubmitRequest
import com.example.leaderboard.domain.score.entity.PuzzleScore
import com.example.leaderboard.domain.score.repository.ScoreRepository
import com.example.leaderboard.domain.user.repository.UserRepository
import org.redisson.api.RedissonClient
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.TimeUnit

private const val RATE_LIMIT_SECONDS = 10L
private const val RATE_LIMIT_MAX = 3L
private const val LOCK_WAIT_SEC = 5L
private const val LOCK_LEASE_SEC = 3L

@Service
@Transactional(readOnly = true)
class ScoreService(
    private val scoreRepository: ScoreRepository,
    private val userRepository: UserRepository,
    private val redisTemplate: RedisTemplate<String, String>,
    private val redissonClient: RedissonClient,
) {

    @Transactional
    fun submitScore(request: ScoreSubmitRequest): ScoreResponse {
        if (!userRepository.existsById(request.userId)) {
            throw NoSuchElementException("유저를 찾을 수 없습니다. id=${request.userId}")
        }

        // Rate Limiting: Redis INCR + EXPIRE (10초 내 3회 초과 시 is_valid=false)
        val isValid = checkRateLimit(request.userId, request.puzzleId)

        val lockKey = "lock:score:${request.userId}:${request.puzzleId}"
        val lock = redissonClient.getLock(lockKey)

        if (!lock.tryLock(LOCK_WAIT_SEC, LOCK_LEASE_SEC, TimeUnit.SECONDS)) {
            throw IllegalStateException("잠시 후 다시 시도해주세요.")
        }

        return try {
            val score = scoreRepository.save(
                PuzzleScore(
                    userId = request.userId,
                    puzzleId = request.puzzleId,
                    score = request.score,
                    elapsedSec = request.elapsedSec,
                    isValid = isValid,
                )
            )

            // 유효한 점수만 Redis ZSET에 누적
            if (isValid) {
                val zsetKey = "ranking:${request.puzzleId}"
                redisTemplate.opsForZSet()
                    .incrementScore(zsetKey, request.userId.toString(), request.score.toDouble())
            }

            ScoreResponse.from(score)
        } finally {
            if (lock.isHeldByCurrentThread) lock.unlock()
        }
    }

    private fun checkRateLimit(userId: Long, puzzleId: String): Boolean {
        val key = "rate:$userId:$puzzleId"
        val ops = redisTemplate.opsForValue()
        val count = ops.increment(key) ?: 1L
        if (count == 1L) {
            redisTemplate.expire(key, RATE_LIMIT_SECONDS, TimeUnit.SECONDS)
        }
        return count <= RATE_LIMIT_MAX
    }
}
