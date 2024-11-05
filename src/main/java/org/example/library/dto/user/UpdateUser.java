package org.example.library.dto.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUser {

    private final static String EMAIL_REGEX =
            "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private final static String NAME_REGEX = "^[A-Z][a-z]*(?:-[A-Z][a-z]*)*$";

    @NotNull(message = "Email can't be null")
    @Pattern(regexp =  EMAIL_REGEX, message = "Невірний формат електронної адреси!")
    private String email;

    @Pattern(regexp =  NAME_REGEX,
            message = "Невірний формат імені! Ім'я має починатися з великої літери та містити тільки літери!")
    private String firstname;

    @Pattern(regexp =  NAME_REGEX,
            message = "Невірний формат прізвища! Прізвище має починатися з великої літери та містити тільки літери!")
    private String lastname;

}
