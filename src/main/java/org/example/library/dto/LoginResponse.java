package org.example.library.dto;

public record LoginResponse(
        String email,
        String token
) {
}
