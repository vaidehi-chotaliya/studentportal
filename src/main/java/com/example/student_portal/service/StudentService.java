package com.example.student_portal.service;

import com.example.student_portal.dto.ChangePasswordRequest;
import com.example.student_portal.entity.Student;
import com.example.student_portal.exception.ApiException;
import com.example.student_portal.repository.StudentRepository;
import com.example.student_portal.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {


    private final StudentRepository studentRepo; // Marked as final
    private final PasswordEncoder passwordEncoder; // Marked as final
    private final JwtUtil jwtUtil; // Marked as final

    private final EmailService emailService;

    public void changePassword(String studentId, ChangePasswordRequest request) {
        Student student = studentRepo.findById(studentId)
                .orElseThrow(ApiException::studentNotFound);

        if (!passwordEncoder.matches(request.getOldPassword(), student.getPassword())) {
            throw ApiException.passwordNotMatch();
        }

        student.setPassword(passwordEncoder.encode(request.getNewPassword()));
        studentRepo.save(student);
    }
}



