package com.example.leaderboard.domain.user.service;

import com.example.leaderboard.domain.user.dto.UserCreateRequest;
import com.example.leaderboard.domain.user.dto.UserResponse;
import com.example.leaderboard.domain.user.entity.User;
import com.example.leaderboard.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserCreateRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("이미 사용 중인 username입니다.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 email입니다.");
        }
        User user = new User(
                request.getUsername(),
                request.getEmail(),
                String.valueOf(request.getPassword().hashCode())
        );
        return UserResponse.from(userRepository.save(user));
    }

    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("유저를 찾을 수 없습니다. id=" + id));
        return UserResponse.from(user);
    }

    public List<UserResponse> getUsers() {
        return userRepository.findAll().stream()
                .map(UserResponse::from)
                .toList();
    }
}
