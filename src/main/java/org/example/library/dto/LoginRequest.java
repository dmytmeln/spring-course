package org.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Ви повинні вказати емейл!")
        String email,
        @NotBlank(message = "Ви повинні вказати пароль!")
        @Size(min = 8, message = "Пароль має містити принаймні 8 символів!")
        String password
) {
}