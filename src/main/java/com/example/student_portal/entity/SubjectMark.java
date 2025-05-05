package com.example.student_portal.entity;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subjectMark")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectMark {

    private String subjectName;

    private int marksObtained;
    private int totalMarks;


}
