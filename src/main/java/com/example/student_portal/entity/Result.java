package com.example.student_portal.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "result")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Result {

    @Id
    private String id;

    private String studentId;   //Student._id

    private int semester;       // 1-8

    private List<SubjectMark> subjects; // list of subject marks

    private double sgpa;         // semester GPA

    private double percentage;   // semester %

    private LocalDateTime createdAt;
}

