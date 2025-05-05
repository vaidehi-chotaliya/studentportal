package com.example.student_portal.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MeritStudentDto {
    private String studentId;
    private double percentage;
    private double sgpa;
    private int rank;
    private int starPoints;
}
