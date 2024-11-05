package org.example.library.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.example.library.dto.user.UserResponse;

import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
public class ReviewDto {

    @JsonProperty(access = READ_ONLY)
    private Integer id;

    @NotNull(message = "Ви повинні вказати рейтинг!")
    @Min(1)
    @Max(5)
    private Byte rating;

    @JsonProperty(access = READ_ONLY)
    private LocalDate createdAt;

    private String comment;

    @JsonProperty(access = READ_ONLY)
    private UserResponse user;

}
