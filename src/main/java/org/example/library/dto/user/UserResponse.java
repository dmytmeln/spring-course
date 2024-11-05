package org.example.library.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
public class UserResponse {

    @JsonProperty(access = READ_ONLY)
    private Integer id;
    private String email;
    private String firstname;
    private String lastname;
    @JsonProperty(access = READ_ONLY)
    private String role;

}
