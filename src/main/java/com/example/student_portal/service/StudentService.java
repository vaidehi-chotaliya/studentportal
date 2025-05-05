package com.example.student_portal.service;

import com.example.student_portal.dto.ChangePasswordRequest;
import com.example.student_portal.entity.*;
import com.example.student_portal.exception.ApiException;
import com.example.student_portal.repository.AttendanceRepository;
import com.example.student_portal.repository.MonthlyReportRepository;
import com.example.student_portal.repository.ResultRepository;
import com.example.student_portal.repository.StudentRepository;
import com.example.student_portal.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository studentRepo;
//    private final MonthlyReportRepository monthlyReportRepo;
    private final MonthlyReportRepository monthlyReportRepo;
    private final AttendanceRepository attendanceRepo;
    private final ResultRepository resultRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    // ✅ Change Password
    public void changePassword(String studentId, ChangePasswordRequest request) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(ApiException::studentNotFound);

        if (!passwordEncoder.matches(request.getOldPassword(), student.getPassword())) {
            throw ApiException.passwordNotMatch();
        }

        student.setPassword(passwordEncoder.encode(request.getNewPassword()));
        studentRepo.save(student);
    }

    // ✅ Get Top 8 Semester Results
    public List<Result> getStudentResults(String studentId) {
        return resultRepo.findTop8ByStudentIdOrderBySemesterDesc(studentId);
    }

    public List<Map<String, Object>> getSemesterMeritList(int semester) {
        List<Result> results = resultRepo.findBySemester(semester);

        return results.stream()
                .sorted(Comparator.comparing(Result::getPercentage).reversed())
                .map(result -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("studentId", result.getStudentId());
                    map.put("percentage", result.getPercentage());
                    map.put("sgpa", result.getSgpa());
                    map.put("semester", result.getSemester());
                    map.put("points", getPointFromPercentage(result.getPercentage())); // ⭐ Points
                    return map;
                })
                .collect(Collectors.toList());
    }


    // ✅ Get Student Rank in a Semester (with support for ties)
    public int calculateStudentRank(String studentId, int semester) {
        List<Result> results = resultRepo.findBySemester(semester)
                .stream()
                .sorted(Comparator.comparing(Result::getPercentage).reversed())
                .collect(Collectors.toList());
        int rank = 1;
        double lastPercentage = -1;
        int actualRank = -1;

        for (int i = 0; i < results.size(); i++) {
            Result res = results.get(i);
            if (i == 0 || res.getPercentage() != lastPercentage) {
                rank = i + 1;
                lastPercentage = res.getPercentage();
            }

            if (res.getStudentId().equals(studentId)) {
                actualRank = rank;
                break;
            }
        }
        return actualRank;
    }

    // ✅ Get Cumulative Report (CGPA + SGPA Trend + Fail Analytics)
    public Map<String, Object> getCumulativeReport(String studentId) {
        List<Result> results = resultRepo.findByStudentId(studentId);

        results.sort(Comparator.comparing(Result::getSemester));
        List<Double> sgpas = results.stream().map(Result::getSgpa).collect(Collectors.toList());
        double cgpa = sgpas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);

        // Course failure count (analytics)
        Map<String, Long> failCountBySubject = results.stream()
                .flatMap(r -> r.getSubjects().stream()) // assuming Result contains List<SubjectResult>
                .filter(s -> s.getMarksObtained() < 35)
                .collect(Collectors.groupingBy(
                        s -> s.getSubjectName(), Collectors.counting()
                ));

        Map<String, Object> report = new HashMap<>();
        report.put("sgpaTrend", sgpas);
        report.put("cgpa", cgpa);
        report.put("failAnalytics", failCountBySubject);

        return report;
    }


    // Star Points Based on Performance
    public int getPointFromPercentage(double percentage) {
        if (percentage >= 90) return 5;
        else if (percentage >= 75) return 4;
        else if (percentage >= 35) return 3;
        else return 0;
    }


}
