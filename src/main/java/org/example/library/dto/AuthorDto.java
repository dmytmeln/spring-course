package org.example.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class AuthorDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @NotBlank(message = "Ви повинні вказати ім'я!")
    private String fullName;

    @NotBlank(message = "Ви повинні вказати країну!")
    private String country;

    @NotNull(message = "Ви повинні вказати рік народження!")
    private Short birthYear;

    private Short deathYear;

    private String biography;

}
