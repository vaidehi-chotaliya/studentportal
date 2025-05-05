package com.example.student_portal.repository;

import com.example.student_portal.entity.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    long countByStudentIdAndDateBetweenAndPresentTrue(String studentId, LocalDateTime start, LocalDateTime end);

    List<Attendance> findByStudentIdAndDateBetween(String studentId, LocalDateTime start, LocalDateTime end);
}
