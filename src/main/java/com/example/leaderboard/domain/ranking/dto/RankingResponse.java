package com.example.leaderboard.domain.ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RankingResponse {
    private final String puzzleId;
    private final List<RankingEntry> entries;
}
