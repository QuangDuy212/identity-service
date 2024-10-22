package com.quangduy.identity_service.service;

import java.util.List;
import java.util.stream.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.quangduy.identity_service.dto.request.UserCreationRequest;
import com.quangduy.identity_service.dto.request.UserUpdateRequest;
import com.quangduy.identity_service.dto.response.UserResponse;
import com.quangduy.identity_service.entity.User;
import com.quangduy.identity_service.exception.AppException;
import com.quangduy.identity_service.exception.ErrorCode;
import com.quangduy.identity_service.mapper.UserMapper;
import com.quangduy.identity_service.repository.UserRepository;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponse createUser(UserCreationRequest request) {
        User user = new User();

        if (this.userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        user = this.userMapper.toUser(request);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = this.userRepository.save(user);
        return this.userMapper.toUserResponse(user);
    }

    public List<UserResponse> getUsers() {
        return this.userRepository.findAll()
                .stream()
                .map(i -> this.userMapper.toUserResponse(i))
                .collect(Collectors.toList());
    }

    public UserResponse getUser(String userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return this.userMapper.toUserResponse(user);
    }

    public UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        this.userMapper.updateUser(user, request);
        user = this.userRepository.save(user);

        return this.userMapper.toUserResponse(user);
    }

    public void deleteUser(String userId) {
        this.userRepository.deleteById(userId);
    }
}
