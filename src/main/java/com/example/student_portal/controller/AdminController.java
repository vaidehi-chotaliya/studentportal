package com.example.student_portal.controller;


import com.example.student_portal.dto.AddResultRequest;
import com.example.student_portal.dto.ApiResponse;
import com.example.student_portal.entity.Result;
import com.example.student_portal.service.AdminService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
//@NoArgsConstructor(force = true)
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('Admin')")
public class AdminController {


    private final AdminService adminService;

    @PostMapping("/result/add")
    public ResponseEntity<ApiResponse<Result>> addResult(@RequestBody AddResultRequest request) {
        System.out.println("AddResultRequest " + request);
        assert adminService != null;
        Result result = adminService.addOrUpdateResult(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Result added/updated successfully", result));
    }




}
