package com.example.student_portal.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AttendanceRequest  {
    private String studentId;
    private LocalDateTime date; // lecture date
    private boolean present;
}
