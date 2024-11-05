package org.example.library.factory;

import org.example.library.domain.Role;
import org.example.library.domain.User;
import org.example.library.dto.user.CreateUser;
import org.example.library.dto.user.UpdateUser;
import org.example.library.dto.user.UpdateUserPassword;
import org.example.library.dto.user.UserResponse;

public class UserFactory {

    public static final Integer DEFAULT_ID = 5;
    public static final String DEFAULT_EMAIL = "john.doe@gmail.com";
    public static final String DEFAULT_FIRSTNAME = "John";
    public static final String DEFAULT_LASTNAME = "Doe";
    public static final String DEFAULT_PASSWORD = "asAS12!@";
    public static final String DEFAULT_NEW_PASSWORD = "!@12ASas";
    public static final Role DEFAULT_ROLE = Role.ROLE_USER;

    public static User newDefaultUser() {
        return User.builder()
                .id(DEFAULT_ID)
                .email(DEFAULT_EMAIL)
                .firstname(DEFAULT_FIRSTNAME)
                .lastname(DEFAULT_LASTNAME)
                .password(DEFAULT_PASSWORD)
                .role(DEFAULT_ROLE)
                .build();
    }

    public static User newDefaultUserWithoutId() {
        return User.builder()
                .email(DEFAULT_EMAIL)
                .firstname(DEFAULT_FIRSTNAME)
                .lastname(DEFAULT_LASTNAME)
                .password(DEFAULT_PASSWORD)
                .role(DEFAULT_ROLE)
                .build();
    }

    public static CreateUser newDefaultCreateUser() {
        return CreateUser.builder()
                .email(DEFAULT_EMAIL)
                .firstname(DEFAULT_FIRSTNAME)
                .lastname(DEFAULT_LASTNAME)
                .password(DEFAULT_PASSWORD)
                .build();
    }

    public static UpdateUser newDefaultUpdateUser() {
        return UpdateUser.builder()
                .email(DEFAULT_EMAIL)
                .firstname(DEFAULT_FIRSTNAME)
                .lastname(DEFAULT_LASTNAME)
                .build();
    }

    public static UpdateUserPassword newDefaultUpdateUserPassword() {
        return new UpdateUserPassword(DEFAULT_PASSWORD, DEFAULT_NEW_PASSWORD);
    }

    public static UserResponse newDefaultUserResponse() {
        return UserResponse.builder()
                .id(DEFAULT_ID)
                .email(DEFAULT_EMAIL)
                .firstname(DEFAULT_FIRSTNAME)
                .lastname(DEFAULT_LASTNAME)
                .role(DEFAULT_ROLE.name())
                .build();
    }

}
