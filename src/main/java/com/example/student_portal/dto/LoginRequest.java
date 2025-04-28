package com.example.student_portal.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String emailOrEnrollment;
    private String password;
}
