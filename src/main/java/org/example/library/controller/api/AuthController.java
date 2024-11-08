package org.example.library.controller.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.library.dto.LoginRequest;
import org.example.library.dto.LoginResponse;
import org.example.library.dto.user.CreateUser;
import org.example.library.dto.user.UserResponse;
import org.example.library.security.UserDetailsImpl;
import org.example.library.service.UserService;
import org.example.library.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signin(@RequestBody @Valid CreateUser requestBody) {
        return ok(service.createUser(requestBody));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password())
            );
            var user = ((UserDetailsImpl)authentication.getPrincipal()).getUser();
            var jwtToken = jwtUtils.generateTokenFromUsername(user.getEmail());
            var authResponse = new LoginResponse(user.getEmail(), jwtToken);
            return ok().body(authResponse);
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
