package com.example.leaderboard.domain.score.controller;

import com.example.leaderboard.common.response.ApiResponse;
import com.example.leaderboard.domain.score.dto.ScoreResponse;
import com.example.leaderboard.domain.score.dto.ScoreSubmitRequest;
import com.example.leaderboard.domain.score.service.ScoreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/scores")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ScoreResponse> submitScore(@Valid @RequestBody ScoreSubmitRequest request) {
        return ApiResponse.ok(scoreService.submitScore(request));
    }
}
