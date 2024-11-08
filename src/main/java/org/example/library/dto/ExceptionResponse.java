package org.example.library.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ExceptionResponse {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> errors;

    public ExceptionResponse(String message) {
        this.message = message;
    }

    public ExceptionResponse(Map<String, String> errors) {
        this.errors = errors;
    }

    public static ExceptionResponse of(String message)  {
        return new ExceptionResponse(message);
    }

    public static ExceptionResponse of(Map<String, String> errors)  {
        return new ExceptionResponse(errors);
    }
}
