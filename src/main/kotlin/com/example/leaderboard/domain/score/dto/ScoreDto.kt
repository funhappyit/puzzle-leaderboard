package com.example.leaderboard.domain.score.dto

import com.example.leaderboard.domain.score.entity.PuzzleScore
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive
import java.time.LocalDateTime

data class ScoreSubmitRequest(
    @field:Positive
    val userId: Long,
    @field:NotBlank
    val puzzleId: String,
    @field:Min(0)
    val score: Int,
    @field:Positive
    val elapsedSec: Int,
)

data class ScoreResponse(
    val id: Long,
    val userId: Long,
    val puzzleId: String,
    val score: Int,
    val elapsedSec: Int,
    val submittedAt: LocalDateTime,
    val isValid: Boolean,
) {
    companion object {
        fun from(s: PuzzleScore) = ScoreResponse(
            id = s.id,
            userId = s.userId,
            puzzleId = s.puzzleId,
            score = s.score,
            elapsedSec = s.elapsedSec,
            submittedAt = s.submittedAt,
            isValid = s.isValid,
        )
    }
}
