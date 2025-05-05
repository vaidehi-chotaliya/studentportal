package com.example.student_portal.service;

import com.example.student_portal.dto.AttendanceRequest;
import com.example.student_portal.entity.Attendance;
import com.example.student_portal.repository.AttendanceRepository;
import com.example.student_portal.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepo;
    private final StudentRepository studentRepo;

    public void markAttendance(AttendanceRequest request) {
        if (!studentRepo.existsById(request.getStudentId())) {
            throw new RuntimeException("Student not found");
        }

        Attendance attendance = Attendance.builder()
                .studentId(request.getStudentId())
                .date(request.getDate())
                .present(request.isPresent())
                .build();

        attendanceRepo.save(attendance);
    }

    // Batch attendance for whole class (Optional)
    public void markBulkAttendance(List<AttendanceRequest> requests) {
        List<Attendance> list = requests.stream()
                .map(req -> Attendance.builder()
                        .studentId(req.getStudentId())
                        .date(req.getDate())
                        .present(req.isPresent())
                        .build())
                .collect(Collectors.toList());
        attendanceRepo.saveAll(list);
    }
}

