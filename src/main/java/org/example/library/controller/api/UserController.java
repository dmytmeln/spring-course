package org.example.library.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.user.UpdateUser;
import org.example.library.dto.user.UpdateUserPassword;
import org.example.library.dto.user.UserResponse;
import org.example.library.security.UserDetailsImpl;
import org.example.library.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<UserResponse> getPersonalInformation(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ok(service.getExistingUserResponse(userDetails.getUserId()));
    }

    @PutMapping
    public ResponseEntity<UserResponse> updatePersonalInformation(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody @Valid UpdateUser requestBody
    ) {
        return ok(service.updateUser(userDetails.getUserId(), requestBody));
    }

    @PutMapping("/password")
    public ResponseEntity<UserResponse> updatePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody UpdateUserPassword requestBody
    ) {
        return ok(service.updateUserPassword(userDetails.getUserId(), requestBody));
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePersonalAccount(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        service.deleteUser(userDetails.getUserId());
        return noContent().build();
    }

}
