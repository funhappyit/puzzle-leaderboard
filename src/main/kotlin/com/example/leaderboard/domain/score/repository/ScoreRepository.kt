package com.example.leaderboard.domain.score.repository

import com.example.leaderboard.domain.score.entity.PuzzleScore
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ScoreRepository : JpaRepository<PuzzleScore, Long> {
    fun countByUserIdAndSubmittedAtAfter(userId: Long, after: LocalDateTime): Long
}
