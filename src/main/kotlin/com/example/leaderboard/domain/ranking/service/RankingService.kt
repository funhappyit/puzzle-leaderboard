package com.example.leaderboard.domain.ranking.service

import com.example.leaderboard.domain.ranking.dto.RankingEntry
import com.example.leaderboard.domain.ranking.dto.RankingResponse
import com.example.leaderboard.domain.ranking.repository.RankingRepository
import com.example.leaderboard.domain.user.repository.UserRepository
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RankingService(
    private val rankingRepository: RankingRepository,
    private val userRepository: UserRepository,
    private val redisTemplate: RedisTemplate<String, String>,
) {

    fun getRanking(puzzleId: String, limit: Int = 100): RankingResponse {
        val zsetKey = "ranking:$puzzleId"
        val tuples = redisTemplate.opsForZSet()
            .reverseRangeWithScores(zsetKey, 0, limit - 1L)

        // Redis ZSET에 데이터가 없으면 DB 풀스캔으로 fallback
        if (tuples.isNullOrEmpty()) {
            return RankingResponse(
                puzzleId = puzzleId,
                entries = rankingRepository.findTopRankingByPuzzleId(puzzleId, limit),
            )
        }

        val userIds = tuples.mapNotNull { it.value?.toLongOrNull() }
        val userMap = userRepository.findAllByIdIn(userIds).associateBy { it.id }

        val entries = tuples.mapIndexedNotNull { index, tuple ->
            val userId = tuple.value?.toLongOrNull() ?: return@mapIndexedNotNull null
            val username = userMap[userId]?.username ?: return@mapIndexedNotNull null
            RankingEntry(
                rank = index + 1,
                userId = userId,
                username = username,
                totalScore = tuple.score?.toLong() ?: 0L,
            )
        }

        return RankingResponse(puzzleId = puzzleId, entries = entries)
    }
}
