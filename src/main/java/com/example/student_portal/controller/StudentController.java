package com.example.student_portal.controller;

import com.example.student_portal.dto.ApiResponse;
import com.example.student_portal.dto.ChangePasswordRequest;
import com.example.student_portal.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@PreAuthorize("hasRole('STUDENT')")
public class StudentController {

    @Autowired
    StudentService studentService;

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(Authentication auth) {
        String studentId = auth.getName(); // JWT subject
        System.out.println("auth ... " + auth);
        return ResponseEntity.ok("Hello Student: " + studentId);
    }

    @PutMapping("/change-password")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<ApiResponse<String>> changePassword(
            Authentication auth,
            @RequestBody @Valid ChangePasswordRequest request
    ) {

        String studentId = auth.getName(); // JWT subject
        studentService.changePassword(studentId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Password changed successfully", null));
    }
}
