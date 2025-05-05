package com.example.student_portal.controller;

import com.example.student_portal.dto.ApiResponse;
import com.example.student_portal.dto.ChangePasswordRequest;
import com.example.student_portal.dto.MeritStudentDto;
import com.example.student_portal.entity.Result;
import com.example.student_portal.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<ApiResponse<String>> changePassword(
            Authentication auth,
            @RequestBody @Valid ChangePasswordRequest request
    ) {

        String studentId = auth.getName(); // JWT subject
        studentService.changePassword(studentId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Password changed successfully", null));
    }

    @GetMapping("/my-results")
    public ResponseEntity<ApiResponse<List<Result>>> getMyResults(Principal principal) {
        System.out.println("principal " + principal);
        String studentId = principal.getName();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched successfully", studentService.getStudentResults(studentId)));
    }


    @GetMapping("/merit-list/{semester}")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getMeritList(@PathVariable int semester) {
        List<Map<String, Object>> meritList = studentService.getSemesterMeritList(semester);
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched merit list", meritList));
    }


    @GetMapping("/my-rank/{semester}")
    public ResponseEntity<ApiResponse<Integer>> getMyRank(@PathVariable int semester, Principal principal) {
        String studentId = principal.getName();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched rank", studentService.calculateStudentRank(studentId, semester)));
    }

    @GetMapping("/cumulative-report")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getCumulativeReport(Principal principal) {
        String studentId = principal.getName();
        return ResponseEntity.ok(new ApiResponse<>(true, "Fetched report", studentService.getCumulativeReport(studentId)));
    }



}
