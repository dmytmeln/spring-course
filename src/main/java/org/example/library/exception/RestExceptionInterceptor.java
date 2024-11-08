package org.example.library.exception;

import org.example.library.dto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

import static java.util.stream.Collectors.toMap;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class RestExceptionInterceptor {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse handleNotFoundException(NotFoundException exception) {
        return ExceptionResponse.of(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleBadRequestException(BadRequestException exception) {
        return ExceptionResponse.of(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleIllegalArgumentException(IllegalArgumentException exception) {
        return ExceptionResponse.of(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        var bindingResult = ex.getBindingResult();

        if (bindingResult.getFieldErrors().isEmpty()) {
            String message = Optional.ofNullable(bindingResult.getGlobalError())
                    .map(ObjectError::getDefaultMessage)
                    .orElse(ex.getMessage());
            return ExceptionResponse.of(message);
        }

        var errors = bindingResult.getFieldErrors().stream()
                .collect(toMap(
                        FieldError::getField,
                        fieldError -> Optional.ofNullable(fieldError.getDefaultMessage())
                                .orElse("Invalid value")
                ));
        return ExceptionResponse.of(errors);
    }

}
