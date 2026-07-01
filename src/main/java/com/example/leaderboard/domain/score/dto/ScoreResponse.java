package com.example.leaderboard.domain.score.dto;

import com.example.leaderboard.domain.score.entity.PuzzleScore;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScoreResponse {

    private final Long id;
    private final Long userId;
    private final String puzzleId;
    private final Integer score;
    private final Integer elapsedSec;
    private final LocalDateTime submittedAt;
    private final Boolean isValid;

    private ScoreResponse(PuzzleScore s) {
        this.id = s.getId();
        this.userId = s.getUserId();
        this.puzzleId = s.getPuzzleId();
        this.score = s.getScore();
        this.elapsedSec = s.getElapsedSec();
        this.submittedAt = s.getSubmittedAt();
        this.isValid = s.getIsValid();
    }

    public static ScoreResponse from(PuzzleScore s) {
        return new ScoreResponse(s);
    }
}
