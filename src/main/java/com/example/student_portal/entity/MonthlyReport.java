package com.example.student_portal.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "monthly_reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MonthlyReport {
    @Id
    private String id;

    private String studentId;
    private String fullName;
    private int month;
    private int year;

    private int totalLecturesAttended;
    private int attendancePoints;

    private double avgPercentage;
    private int performancePoints;

    private int totalPoints; // attendance + performance

    private int monthlyRank;

    private LocalDateTime generatedAt;
}

