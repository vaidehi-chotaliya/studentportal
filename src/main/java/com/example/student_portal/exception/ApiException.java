package com.example.student_portal.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {

    private final String errorCode;
    private final String message;
    private final String details;
    private final HttpStatus status;


    // Predefined static factory method
    public static ApiException emailAlreadyExists(String email) {
        return new ApiException(
                "EMAIL_ALREADY_EXISTS",
                "Email already exists",
                "Attempted to register with existing email: " + email,
                HttpStatus.CONFLICT
        );
    }

    public static ApiException invalidToken() {
        return new ApiException(
                "INVALID_TOKEN",
                "Invalid or expired token",
                "Invalid or expired token",
                HttpStatus.BAD_REQUEST
        );
    }

    public static ApiException studentNotFound() {
        return new ApiException(
                "STUDENT_NOT_FOUND",
                "Student not found",
                "Invalid token",
                HttpStatus.NOT_FOUND
        );
    }

    public static ApiException invalidPassword() {
        return new ApiException(
                "INVALID_PASSWORD",
                "Password is incorrect",
                "Invalid password",
                HttpStatus.UNAUTHORIZED
        );
    }

    public static ApiException passwordNotMatch() {
        return new ApiException(
                "PASSWORD_DOSE_NOT_MATCH",
                "Old password and given password doesn't matched",
                "Password not matched",
                HttpStatus.BAD_REQUEST
        );
    }

}