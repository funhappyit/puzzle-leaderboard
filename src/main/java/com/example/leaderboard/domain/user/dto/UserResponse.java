package com.example.leaderboard.domain.user.dto;

import com.example.leaderboard.domain.user.entity.User;
import com.example.leaderboard.domain.user.entity.UserStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final UserStatus status;
    private final LocalDateTime createdAt;

    private UserResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.status = user.getStatus();
        this.createdAt = user.getCreatedAt();
    }

    public static UserResponse from(User user) {
        return new UserResponse(user);
    }
}
