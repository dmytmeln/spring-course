package org.example.library.service;

import org.example.library.domain.User;
import org.example.library.dto.user.CreateUser;
import org.example.library.dto.user.UpdateUser;
import org.example.library.dto.user.UserResponse;
import org.example.library.exception.BadRequestException;
import org.example.library.exception.NotFoundException;
import org.example.library.mapper.UserMapper;
import org.example.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.example.library.factory.UserFactory.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService service;

    @Test
    public void givenUsersInDb_whenGetAll_thenReturnUserResponseList() {
        var expectedResponses = setupMockedUserListInDb();

        var actualResponses = service.getAllUsers();

        assertIterableEquals(expectedResponses, actualResponses);
    }

    private List<UserResponse> setupMockedUserListInDb() {
        var users = List.of(newDefaultUser());
        var responses = List.of(newDefaultUserResponse());
        when(repository.findAll()).thenReturn(users);
        when(mapper.toResponse(users)).thenReturn(responses);
        return responses;
    }

    @Test
    public void givenNoUsersInDb_whenGetAll_thenReturnEmptyUserResponseList() {
        when(repository.findAll()).thenReturn(List.of());
        when(mapper.toResponse(List.of())).thenReturn(List.of());

        var actualResponses = service.getAllUsers();

        assertTrue(actualResponses.isEmpty());
    }

    @Test
    public void givenExistingId_whenGetUser_thenReturnUser() {
        var expectedUser = newDefaultUser();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(expectedUser));

        var actualUser = service.getExistingUser(DEFAULT_ID);

        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void givenNotExistingId_whenGetUser_thenThrowNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.getExistingUser(DEFAULT_ID)
        );
    }

    @Test
    public void givenExistingId_whenGetUserResponse_thenReturnResponse() {
        var expectedResponse = setupMockedUserInDbMappedToResponse();

        var actualResponse = service.getExistingUserResponse(DEFAULT_ID);

        assertEquals(expectedResponse, actualResponse);
    }

    private UserResponse setupMockedUserInDbMappedToResponse() {
        var user = newDefaultUser();
        var response = newDefaultUserResponse();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(response);
        return response;
    }

    @Test
    public void givenNotExistingId_whenGetUserResponse_thenThrowNotFoundException() {
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.getExistingUserResponse(DEFAULT_ID)
        );
    }

    @Test
    public void givenCreateUserRequest_whenCreateUser_thenReturnSavedUserResponse() {
        var inputRequest = newDefaultCreateUser();
        var expectedResponse = setupMockedUserCreationFlow(inputRequest);

        var actualResponse = service.createUser(inputRequest);

        assertEquals(expectedResponse, actualResponse);
    }

    private UserResponse setupMockedUserCreationFlow(CreateUser request) {
        var user = newDefaultUserWithoutId();
        var savedUser = newDefaultUser();
        var savedResponse = newDefaultUserResponse();
        when(repository.existsByEmail(DEFAULT_EMAIL)).thenReturn(false);
        when(mapper.toEntity(request)).thenReturn(user);
        when(repository.save(user)).thenReturn(savedUser);
        when(mapper.toResponse(savedUser)).thenReturn(savedResponse);
        return savedResponse;
    }

    @Test
    public void givenExistingEmail_whenCreateUser_thenThrowIllegalArgumentException() {
        var request = newDefaultCreateUser();
        when(repository.existsByEmail(DEFAULT_EMAIL)).thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.createUser(request)
        );
    }

    @Test
    public void givenUpdateUserRequest_whenUpdateUser_thenReturnUpdatedUserResponse() {
        var inputRequest = newDefaultUpdateUser();
        var expectedResponse = setupMockedUserUpdateFlow(inputRequest);

        var actualResponse = service.updateUser(DEFAULT_ID, inputRequest);

        assertEquals(expectedResponse, actualResponse);
        assertEquals(DEFAULT_ID, actualResponse.getId());
    }

    private UserResponse setupMockedUserUpdateFlow(UpdateUser request) {
        var existingUser = newDefaultUser();
        var updatedUser = newDefaultUser();
        var updatedResponse = newDefaultUserResponse();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(existingUser));
        updatedUser.setFirstname(request.getFirstname());
        updatedUser.setLastname(request.getLastname());
        updatedUser.setEmail(request.getEmail());
        when(repository.save(any(User.class))).thenReturn(updatedUser);
        when(mapper.toResponse(updatedUser)).thenReturn(updatedResponse);
        return updatedResponse;
    }

    @Test
    public void givenUpdateUserRequestWithAlreadyExistingEmail_whenUpdateUser_thenThrowIllegalArgumentException() {
        var inputRequest = newDefaultUpdateUser();
        inputRequest.setEmail("new email");
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(newDefaultUser()));
        when(repository.existsByEmail(inputRequest.getEmail())).thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.updateUser(DEFAULT_ID, inputRequest)
        );
    }

    @Test
    public void givenExistingIdAndMismatchedCurrentPassword_whenUpdateUserPassword_thenThrowBadRequestException() {
        var request = newDefaultUpdateUserPassword();
        request.setCurrentPassword(DEFAULT_NEW_PASSWORD);
        var existingUser = newDefaultUser();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(request.getCurrentPassword(), existingUser.getPassword())).thenReturn(false);

        assertThrows(
                BadRequestException.class,
                () -> service.updateUserPassword(DEFAULT_ID, request)
        );
    }

    @Test
    public void givenMatchingCurrentPassword_whenUpdateUserPassword_thenReturnUpdatedUserResponse() {
        var user = newDefaultUser();
        var updatedResponse = setupMockedUpdateUserPasswordFlow(user);

        var actualResponse = service.updateUserPassword(DEFAULT_ID, newDefaultUpdateUserPassword());

        assertEquals(updatedResponse, actualResponse);
        assertEquals(DEFAULT_NEW_PASSWORD, user.getPassword());
    }

    private UserResponse setupMockedUpdateUserPasswordFlow(User existingUser) {
        var updatedResponse = newDefaultUserResponse();
        when(repository.findById(DEFAULT_ID)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.matches(DEFAULT_PASSWORD, existingUser.getPassword())).thenReturn(true);
        when(passwordEncoder.encode(DEFAULT_NEW_PASSWORD)).thenReturn(DEFAULT_NEW_PASSWORD);
        when(repository.save(existingUser)).thenReturn(existingUser);
        when(mapper.toResponse(existingUser)).thenReturn(updatedResponse);
        return updatedResponse;
    }

    @Test
    public void givenAnyId_whenDeleteUser_thenNoExceptionThrown() {
        assertDoesNotThrow(() -> service.deleteUser(DEFAULT_ID));
        assertDoesNotThrow(() -> service.deleteUser(-1));
    }

}
