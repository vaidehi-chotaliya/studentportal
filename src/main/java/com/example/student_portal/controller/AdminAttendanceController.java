package com.example.student_portal.controller;

import com.example.student_portal.dto.ApiResponse;
import com.example.student_portal.dto.AttendanceRequest;
import com.example.student_portal.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/attendance")
@RequiredArgsConstructor
public class AdminAttendanceController {

    private final AttendanceService attendanceService;

    // ✅ Add attendance for one student
    @PostMapping("/mark")
    public ResponseEntity<ApiResponse<String>> markAttendance(@RequestBody AttendanceRequest request) {
        attendanceService.markAttendance(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Attendance marked successfully", null));
    }

    // ✅ Optional: Mark attendance for multiple students at once
    @PostMapping("/mark-bulk")
    public ResponseEntity<String> markBulk(@RequestBody List<AttendanceRequest> requests) {

        attendanceService.markBulkAttendance(requests);
        return ResponseEntity.ok("Bulk attendance saved successfully");
    }
}

