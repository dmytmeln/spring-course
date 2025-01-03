package org.example.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    @NotBlank(message = "Ви повинні вказати назву!")
    private String title;

    @NotNull(message = "Ви повинні вказати рік публікації!")
    private Short publishYear;

    @NotBlank(message = "Ви повинні вказати мову!")
    private String language;

    @NotNull(message = "Ви повинні вказати кількість сторінок!")
    private Short pages;

    private String description;

    @NotEmpty(message = "Ви повинні вказати принаймні одного автора!")
    private List<@Positive(message = "ID автора має бути більшим за 0!") Integer> authorsId;

    @NotNull(message = "Ви повинні вказати категорію!")
    @Positive(message = "ID категорії має бути більшим за 0!")
    private Integer categoryId;

}
