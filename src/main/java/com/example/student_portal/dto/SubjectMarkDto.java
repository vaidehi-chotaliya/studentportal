package com.example.student_portal.dto;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubjectMarkDto {

    private String subjectName;

    private int marksObtained;

    private int totalMarks;

}