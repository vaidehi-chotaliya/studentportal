package com.example.student_portal.dto;

import lombok.Data;

@Data
public class TokenRefreshRequest {
    private String refreshToken;
}
