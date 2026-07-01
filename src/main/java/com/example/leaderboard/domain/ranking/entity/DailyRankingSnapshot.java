package com.example.leaderboard.domain.ranking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "daily_ranking_snapshots")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyRankingSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "snapshot_date", nullable = false)
    private LocalDate snapshotDate;

    @Column(name = "puzzle_id", nullable = false, length = 50)
    private String puzzleId;

    @Column(nullable = false)
    private Integer rank;

    @Column(name = "total_score", nullable = false)
    private Long totalScore;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public DailyRankingSnapshot(Long userId, LocalDate snapshotDate, String puzzleId, Integer rank, Long totalScore) {
        this.userId = userId;
        this.snapshotDate = snapshotDate;
        this.puzzleId = puzzleId;
        this.rank = rank;
        this.totalScore = totalScore;
    }
}
