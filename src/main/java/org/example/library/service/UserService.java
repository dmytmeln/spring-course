package org.example.library.service;

import lombok.RequiredArgsConstructor;
import org.example.library.domain.Library;
import org.example.library.domain.User;
import org.example.library.dto.user.CreateUser;
import org.example.library.dto.user.UpdateUser;
import org.example.library.dto.user.UpdateUserPassword;
import org.example.library.dto.user.UserResponse;
import org.example.library.exception.BadRequestException;
import org.example.library.exception.NotFoundException;
import org.example.library.mapper.UserMapper;
import org.example.library.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        return mapper.toResponse(repository.findAll());
    }

    public User getExistingUser(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Користувача не знайдено!"));
    }

    public UserResponse getExistingUserResponse(Integer id) {
        return mapper.toResponse(getExistingUser(id));
    }

    public UserResponse createUser(CreateUser dto) {
        requireNotExistsByEmail(dto.getEmail());

        var user = mapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        setLibraryToUser(user);
        var savedUser = repository.save(user);
        return mapper.toResponse(savedUser);
    }

    private static void setLibraryToUser(User user) {
        var personalLibrary = new Library();
        personalLibrary.setUser(user);
        user.setLibrary(personalLibrary);
    }

    public UserResponse updateUser(Integer id, UpdateUser dto) {
        var existingUser = requireNotExistsByEmail(id, dto);
        var updatedUser = repository.save(
                existingUser.toBuilder()
                        .firstname(dto.getFirstname())
                        .lastname(dto.getLastname())
                        .email(dto.getEmail())
                        .build());
        return mapper.toResponse(updatedUser);
    }

    private User requireNotExistsByEmail(Integer id, UpdateUser dto) {
        var existingUser = getExistingUser(id);
        if (!existingUser.getEmail().equals(dto.getEmail())) {
            requireNotExistsByEmail(dto.getEmail());
        }
        return existingUser;
    }

    private void requireNotExistsByEmail(String email) {
        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Користувач з таким емейлом вже існує!");
        }
    }

    public UserResponse updateUserPassword(Integer id, UpdateUserPassword dto) {
        var existingUser = requirePasswordsMatches(id, dto.getCurrentPassword());
        existingUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        repository.save(existingUser);
        return mapper.toResponse(existingUser);
    }

    private User requirePasswordsMatches(Integer id, String verificationPassword) {
        var existingUser = getExistingUser(id);
        if (!passwordEncoder.matches(verificationPassword, existingUser.getPassword())) {
            throw new BadRequestException("Паролі не збігаються!");
        }
        return existingUser;
    }

    public void deleteUser(Integer id) {
        repository.deleteById(id);
    }

}
