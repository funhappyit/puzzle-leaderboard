package com.example.leaderboard.domain.score.service

import com.example.leaderboard.domain.score.dto.ScoreResponse
import com.example.leaderboard.domain.score.dto.ScoreSubmitRequest
import com.example.leaderboard.domain.score.entity.PuzzleScore
import com.example.leaderboard.domain.score.repository.ScoreRepository
import com.example.leaderboard.domain.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

private const val RATE_LIMIT_SECONDS = 10L
private const val RATE_LIMIT_MAX = 3

@Service
@Transactional(readOnly = true)
class ScoreService(
    private val scoreRepository: ScoreRepository,
    private val userRepository: UserRepository,
) {

    @Transactional
    fun submitScore(request: ScoreSubmitRequest): ScoreResponse {
        if (!userRepository.existsById(request.userId)) {
            throw NoSuchElementException("유저를 찾을 수 없습니다. id=${request.userId}")
        }

        // Rate Limiting: 10초 내 3회 초과 시 is_valid=false로 기록 (2주차에 Redis로 교체 예정)
        val recentCount = scoreRepository.countByUserIdAndSubmittedAtAfter(
            userId = request.userId,
            after = LocalDateTime.now().minusSeconds(RATE_LIMIT_SECONDS),
        )
        val isValid = recentCount < RATE_LIMIT_MAX

        val score = PuzzleScore(
            userId = request.userId,
            puzzleId = request.puzzleId,
            score = request.score,
            elapsedSec = request.elapsedSec,
            isValid = isValid,
        )
        return ScoreResponse.from(scoreRepository.save(score))
    }
}
