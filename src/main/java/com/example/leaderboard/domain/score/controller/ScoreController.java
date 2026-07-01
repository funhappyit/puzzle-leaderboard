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

    // 케이스 3: Redisson 분산락 + Rate Limiting (기본)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ScoreResponse> submitScore(@Valid @RequestBody ScoreSubmitRequest request) {
        return ApiResponse.ok(scoreService.submitScore(request));
    }

    // 케이스 1: 락 없음 — Race Condition 재현용 (부하 테스트 전용)
    @PostMapping("/no-lock")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ScoreResponse> submitNoLock(@Valid @RequestBody ScoreSubmitRequest request) {
        return ApiResponse.ok(scoreService.submitNoLock(request));
    }

    // 케이스 2: Redis ZINCRBY 원자연산만 (부하 테스트 전용)
    @PostMapping("/redis-only")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ScoreResponse> submitRedisOnly(@Valid @RequestBody ScoreSubmitRequest request) {
        return ApiResponse.ok(scoreService.submitRedisOnly(request));
    }
}
