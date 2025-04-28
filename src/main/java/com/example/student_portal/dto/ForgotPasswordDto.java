package com.example.student_portal.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class ForgotPasswordDto {

    @Email
    private  String email;
}
