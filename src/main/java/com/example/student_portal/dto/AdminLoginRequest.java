package com.example.student_portal.dto;

import lombok.Data;

@Data
public class AdminLoginRequest {
    private String email;
    private String password;
}
