package com.example.leaderboard.domain.score.repository;

import com.example.leaderboard.domain.score.entity.PuzzleScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ScoreRepository extends JpaRepository<PuzzleScore, Long> {
    long countByUserIdAndSubmittedAtAfter(Long userId, LocalDateTime after);
}
