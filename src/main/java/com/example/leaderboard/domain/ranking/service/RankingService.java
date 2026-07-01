package com.example.leaderboard.domain.ranking.service;

import com.example.leaderboard.domain.ranking.dto.RankingEntry;
import com.example.leaderboard.domain.ranking.dto.RankingResponse;
import com.example.leaderboard.domain.ranking.repository.RankingRepository;
import com.example.leaderboard.domain.user.entity.User;
import com.example.leaderboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RankingService {

    private final RankingRepository rankingRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public RankingResponse getRanking(String puzzleId, int limit) {
        String zsetKey = "ranking:" + puzzleId;
        Set<ZSetOperations.TypedTuple<String>> tuples =
                redisTemplate.opsForZSet().reverseRangeWithScores(zsetKey, 0, limit - 1L);

        // Redis에 데이터 없으면 DB fallback
        if (tuples == null || tuples.isEmpty()) {
            return new RankingResponse(puzzleId, rankingRepository.findTopRankingByPuzzleId(puzzleId, limit));
        }

        List<Long> userIds = tuples.stream()
                .filter(t -> t.getValue() != null)
                .map(t -> Long.parseLong(t.getValue()))
                .toList();

        Map<Long, String> userMap = userRepository.findAllByIdIn(userIds).stream()
                .collect(Collectors.toMap(User::getId, User::getUsername));

        List<RankingEntry> entries = new ArrayList<>();
        int rank = 1;
        for (ZSetOperations.TypedTuple<String> tuple : tuples) {
            if (tuple.getValue() == null || tuple.getScore() == null) continue;
            Long userId = Long.parseLong(tuple.getValue());
            String username = userMap.get(userId);
            if (username == null) continue;
            entries.add(new RankingEntry(rank++, userId, username, tuple.getScore().longValue()));
        }

        return new RankingResponse(puzzleId, entries);
    }
}
