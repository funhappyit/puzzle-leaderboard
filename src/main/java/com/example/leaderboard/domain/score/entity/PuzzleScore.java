package com.example.leaderboard.domain.score.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "puzzle_scores")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PuzzleScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "puzzle_id", nullable = false, length = 50)
    private String puzzleId;

    @Column(nullable = false)
    private Integer score;

    @Column(name = "elapsed_sec", nullable = false)
    private Integer elapsedSec;

    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt = LocalDateTime.now();

    @Column(name = "is_valid", nullable = false)
    private Boolean isValid = true;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public PuzzleScore(Long userId, String puzzleId, Integer score, Integer elapsedSec, Boolean isValid) {
        this.userId = userId;
        this.puzzleId = puzzleId;
        this.score = score;
        this.elapsedSec = elapsedSec;
        this.isValid = isValid;
    }
}
