package com.example.student_portal.repository;

import com.example.student_portal.entity.MonthlyReport;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MonthlyReportRepository extends MongoRepository<MonthlyReport, String> {

    // Fetch all reports for a specific month and year
    List<MonthlyReport> findByMonthAndYear(int month, int year);

    // Fetch a specific student's report for a month
    Optional<MonthlyReport> findByStudentIdAndMonthAndYear(String studentId, int month, int year);

    // Fetch all reports of a student (history)
    List<MonthlyReport> findByStudentIdOrderByYearDescMonthDesc(String studentId);

    // Optional: Top students for a month
    List<MonthlyReport> findTop10ByMonthAndYearOrderByTotalPointsDesc(int month, int year);
}

