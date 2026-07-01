package com.example.leaderboard.domain.score.service;

import com.example.leaderboard.domain.score.dto.ScoreResponse;
import com.example.leaderboard.domain.score.dto.ScoreSubmitRequest;
import com.example.leaderboard.domain.score.entity.PuzzleScore;
import com.example.leaderboard.domain.score.repository.ScoreRepository;
import com.example.leaderboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScoreService {

    private static final long RATE_LIMIT_SECONDS = 10L;
    private static final long RATE_LIMIT_MAX = 3L;
    private static final long LOCK_WAIT_SEC = 5L;
    private static final long LOCK_LEASE_SEC = 3L;

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final RedissonClient redissonClient;

    @Transactional
    public ScoreResponse submitScore(ScoreSubmitRequest request) {
        if (!userRepository.existsById(request.getUserId())) {
            throw new NoSuchElementException("유저를 찾을 수 없습니다. id=" + request.getUserId());
        }

        boolean isValid = checkRateLimit(request.getUserId(), request.getPuzzleId());

        String lockKey = "lock:score:" + request.getUserId() + ":" + request.getPuzzleId();
        RLock lock = redissonClient.getLock(lockKey);

        try {
            if (!lock.tryLock(LOCK_WAIT_SEC, LOCK_LEASE_SEC, TimeUnit.SECONDS)) {
                throw new IllegalStateException("잠시 후 다시 시도해주세요.");
            }

            PuzzleScore score = scoreRepository.save(
                    new PuzzleScore(request.getUserId(), request.getPuzzleId(), request.getScore(), request.getElapsedSec(), isValid)
            );

            if (isValid) {
                String zsetKey = "ranking:" + request.getPuzzleId();
                redisTemplate.opsForZSet().incrementScore(zsetKey, String.valueOf(request.getUserId()), request.getScore());
            }

            return ScoreResponse.from(score);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("락 획득 중 인터럽트가 발생했습니다.");
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private boolean checkRateLimit(Long userId, String puzzleId) {
        String key = "rate:" + userId + ":" + puzzleId;
        Long count = redisTemplate.opsForValue().increment(key);
        if (count == null) count = 1L;
        if (count == 1L) {
            redisTemplate.expire(key, RATE_LIMIT_SECONDS, TimeUnit.SECONDS);
        }
        return count <= RATE_LIMIT_MAX;
    }
}
