package com.example.student_portal.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attendance {
    @Id
    private String id;

    private String studentId;

    private LocalDateTime date; // Lecture date & time

    private boolean present; // true = present, false = absent
}
