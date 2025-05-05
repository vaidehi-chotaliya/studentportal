package com.example.student_portal.service;

import com.example.student_portal.dto.AddResultRequest;
import com.example.student_portal.dto.SubjectMarkDto;
import com.example.student_portal.entity.Result;
import com.example.student_portal.entity.SubjectMark;
import com.example.student_portal.repository.ResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final ResultRepository resultRepo;

    // service/ResultService.java
    public Result addOrUpdateResult(AddResultRequest request) {

        double totalMarks = 0;
        double marksObtained = 0;

        for (SubjectMarkDto subject : request.getSubjects()) {
            totalMarks += subject.getTotalMarks();
            marksObtained += subject.getMarksObtained();
        }

        double percentage = (marksObtained / totalMarks) * 100;
        double sgpa = percentageToSGPA(percentage);

        // Convert SubjectMarkDto to SubjectMark
        List<SubjectMark> subjectMarks = request.getSubjects()
                .stream()
                .map(s -> SubjectMark.builder()
                        .subjectName(s.getSubjectName())
                        .marksObtained(s.getMarksObtained())
                        .totalMarks(s.getTotalMarks())
                        .build())
                .toList();

        Result result = Result.builder()
                .studentId(request.getStudentId())
                .semester(request.getSemester())
                .subjects(subjectMarks)
                .percentage(percentage)
                .sgpa(sgpa)
                .createdAt(LocalDateTime.now())
                .build();

        // If result exists for this student and semester, update it
        List<Result> existing = resultRepo.findByStudentIdAndSemester(request.getStudentId(), request.getSemester());

        if (!existing.isEmpty()) {
            result.setId(existing.get(0).getId()); // keep same id
        }

        return resultRepo.save(result);
    }

    private double percentageToSGPA(double percentage) {
        if (percentage >= 90) return 10.0;
        else if (percentage >= 80) return 9.0;
        else if (percentage >= 70) return 8.0;
        else if (percentage >= 60) return 7.0;
        else if (percentage >= 50) return 6.0;
        else if (percentage >= 40) return 5.0;
        else return 0.0;
    }

}
