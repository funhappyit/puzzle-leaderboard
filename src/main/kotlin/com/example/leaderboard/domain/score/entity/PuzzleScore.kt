package com.example.leaderboard.domain.score.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "puzzle_scores")
class PuzzleScore(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "puzzle_id", nullable = false, length = 50)
    val puzzleId: String,

    @Column(nullable = false)
    val score: Int,

    @Column(name = "elapsed_sec", nullable = false)
    val elapsedSec: Int,

    @Column(name = "submitted_at", nullable = false)
    val submittedAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "is_valid", nullable = false)
    val isValid: Boolean = true,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
