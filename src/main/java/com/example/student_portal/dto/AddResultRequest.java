package com.example.student_portal.dto;

import lombok.*;

import java.util.List;

@Data
public class AddResultRequest {

    private String studentId;
    private int semester;
    private List<SubjectMarkDto> subjects;

}
