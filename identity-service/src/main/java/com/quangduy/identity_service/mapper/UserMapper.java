package com.quangduy.identity_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.quangduy.identity_service.dto.request.UserCreationRequest;
import com.quangduy.identity_service.dto.request.UserUpdateRequest;
import com.quangduy.identity_service.dto.response.UserResponse;
import com.quangduy.identity_service.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
