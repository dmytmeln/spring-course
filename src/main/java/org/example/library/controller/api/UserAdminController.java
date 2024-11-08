package org.example.library.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.library.dto.user.UserResponse;
import org.example.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class UserAdminController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ok(service.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer userId) {
        return ok(service.getExistingUserResponse(userId));
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        service.deleteUser(userId);
        return noContent().build();
    }

}
