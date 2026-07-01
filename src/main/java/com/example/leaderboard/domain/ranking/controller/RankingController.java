package com.example.leaderboard.domain.ranking.controller;

import com.example.leaderboard.common.response.ApiResponse;
import com.example.leaderboard.domain.ranking.dto.RankingResponse;
import com.example.leaderboard.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rankings")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;

    @GetMapping
    public ApiResponse<RankingResponse> getRanking(
            @RequestParam String puzzleId,
            @RequestParam(defaultValue = "100") int limit) {
        return ApiResponse.ok(rankingService.getRanking(puzzleId, limit));
    }
}
