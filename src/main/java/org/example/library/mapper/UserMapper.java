package org.example.library.mapper;

import org.example.library.domain.User;
import org.example.library.dto.user.CreateUser;
import org.example.library.dto.user.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    List<UserResponse> toResponse(List<User> users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "library", ignore = true)
    @Mapping(target = "role", constant = "ROLE_USER")
    User toEntity(CreateUser dto);

}
