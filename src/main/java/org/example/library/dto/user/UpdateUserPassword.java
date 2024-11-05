package org.example.library.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateUserPassword {

    private final static String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\[\\]{}|;:',.<>/?`~\\-_=+])[A-Za-z\\d!@#$%^&*()\\[\\]{}|;:',.<>/?`~\\-_=+]{8,}$";

    @NotBlank(message = "Ви повинні вказати поточний пароль!")
    private String currentPassword;

    @Pattern(regexp = PASSWORD_REGEX,
            message = "Невірний формат паролю! Пароль повинен містити щонайменше 8 символів, з яких одна велика " +
                    "літера, одна мала літера, одна цифра та один спеціальний символ.")
    private String newPassword;

}
