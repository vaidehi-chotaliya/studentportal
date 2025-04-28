package com.example.student_portal.controller;

import com.example.student_portal.dto.*;
import com.example.student_portal.entity.Student;
import com.example.student_portal.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Student>> register(@RequestBody @Valid StudentRegisterDto req) {
        Student stud =  authService.registerUser(req);
        return ResponseEntity.ok(new ApiResponse<>(true, "Registration successful", stud));
    }


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest req) {
        AuthResponse res = authService.login(req);
        return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", res));
    }

    @PostMapping("/forgot-password")
    public  ResponseEntity<ApiResponse<String>> forgotPassword(@RequestBody ForgotPasswordDto req){
        String res = authService.forgotPassword(req);
        return ResponseEntity.ok(new ApiResponse<>(true , "Forgot password link sent successfully" , null));
    }

    @PostMapping("/reset-password")
    public  ResponseEntity<ApiResponse<Student>> resetPassword(@RequestBody ResetPasswordRequest req){
        Student res = authService.resetPassword(req);
        return ResponseEntity.ok(new ApiResponse<>(true , "Password changed successfully" , res));
    }

}