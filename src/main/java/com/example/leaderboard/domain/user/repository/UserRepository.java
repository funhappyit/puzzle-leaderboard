package com.example.leaderboard.domain.user.repository;

import com.example.leaderboard.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    List<User> findAllByIdIn(List<Long> ids);
}
