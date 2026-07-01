package com.example.leaderboard.domain.score.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ScoreSubmitRequest {

    @Positive
    private Long userId;

    @NotBlank
    private String puzzleId;

    @Min(0)
    private Integer score;

    @Positive
    private Integer elapsedSec;
}
