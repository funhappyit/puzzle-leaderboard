package com.example.leaderboard.domain.ranking.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name = "daily_ranking_snapshots")
class DailyRankingSnapshot(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(name = "snapshot_date", nullable = false)
    val snapshotDate: LocalDate,

    @Column(name = "puzzle_id", nullable = false, length = 50)
    val puzzleId: String,

    @Column(nullable = false)
    val rank: Int,

    @Column(name = "total_score", nullable = false)
    val totalScore: Long,

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
)
