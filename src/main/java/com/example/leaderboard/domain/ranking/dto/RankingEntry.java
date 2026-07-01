package com.example.leaderboard.domain.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RankingEntry {
    private final int rank;
    private final Long userId;
    private final String username;
    private final Long totalScore;
}
