package com.quangduy.identity_service.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quangduy.identity_service.dto.request.UserCreationRequest;
import com.quangduy.identity_service.dto.request.UserUpdateRequest;
import com.quangduy.identity_service.dto.response.ApiResponse;
import com.quangduy.identity_service.dto.response.UserResponse;
import com.quangduy.identity_service.exception.ErrorCode;
import com.quangduy.identity_service.service.UserService;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/users")
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(this.userService.createUser(request));
        apiResponse.setMessage("Create success");
        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(
                i -> log.info(i.getAuthority()));
        return ApiResponse.<List<UserResponse>>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(this.userService.getUsers())
                .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<UserResponse> getUser(@PathVariable("userId") String userId) {
        return ApiResponse.<UserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(this.userService.getUser(userId))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .code(ErrorCode.SUCCESS.getCode())
                .result(this.userService.getMyInfo())
                .build();
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable("userId") String userId,
            @RequestBody UserUpdateRequest request) {
        return this.userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        this.userService.deleteUser(userId);
        return "User is deleted";
    }
}
