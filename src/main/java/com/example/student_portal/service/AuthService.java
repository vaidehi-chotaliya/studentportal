package com.example.student_portal.service;

import com.example.student_portal.dto.*;
import com.example.student_portal.entity.Admin;
import com.example.student_portal.entity.Student;
import com.example.student_portal.exception.ApiException;
import com.example.student_portal.repository.AdminRepository;
import com.example.student_portal.repository.StudentRepository;
import com.example.student_portal.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.plaf.IconUIResource;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor()
public class AuthService {

    private final StudentRepository studentRepo; // Marked as final
    private final AdminRepository adminRepo; // Marked as final

    private final PasswordEncoder passwordEncoder; // Marked as final
    private final JwtUtil jwtUtil; // Marked as final

    private final EmailService emailService;

    public Student registerUser(StudentRegisterDto req) {
        if (studentRepo.existsByEmail(req.getEmail())) {
            throw ApiException.emailAlreadyExists(req.getEmail());
        }
        String enrollmentNo = "ENR" + UUID.randomUUID().toString().substring(0, 8);

        Student student = Student.builder()
                .fullName(req.getFullName())
                .email(req.getEmail())
                .enrollmentNo(enrollmentNo)
                .password(passwordEncoder.encode(req.getPassword()))
                .role("STUDENT")
                .isVerified(true).build();

        return studentRepo.save(student);

    }

    public AuthResponse login(LoginRequest req) {
        Student student = studentRepo.findByEmailOrEnrollmentNo(req.getEmailOrEnrollment(), req.getEmailOrEnrollment())
                .orElseThrow(ApiException::studentNotFound);


        if (!passwordEncoder.matches(req.getPassword(), student.getPassword())) {
            throw ApiException.invalidPassword();
        }

        String token = jwtUtil.generateToken(student.getId(), student.getRole());
        String refreshToken = UUID.randomUUID().toString();


        // Save refresh token to DB
        student.setRefreshToken(refreshToken);
        student.setRefreshTokenExpiryTime(LocalDateTime.now().plusDays(7)); // Valid for 7 days
        System.out.println("student " + student);
        studentRepo.save(student);

        return new AuthResponse(token, refreshToken, student.getRole());
    }

    public AuthResponse adminLogin(AdminLoginRequest req) {
        Admin admin = adminRepo.findByEmail(req.getEmail())
                .orElseThrow(ApiException::studentNotFound);


        if (!passwordEncoder.matches(req.getPassword(), admin.getPassword())) {
            throw ApiException.invalidPassword();
        }

        String token = jwtUtil.generateToken(admin.getId(), "ADMIN");
        String refreshToken = UUID.randomUUID().toString();
        return new AuthResponse(token, refreshToken, "ADMIN");
    }

    public String forgotPassword(ForgotPasswordDto req) {

        Student student = studentRepo.findByEmail(req.getEmail())
                .orElseThrow(ApiException::studentNotFound);

        String token = UUID.randomUUID().toString();

        student.setResetTokenExpiryTime(LocalDateTime.now().plusMinutes(30));
        student.setResetToken(token);
        studentRepo.save(student);

        String resetUrl = "localhost:8082/auth/reset-password?token=" + token;
        emailService.sendResetEmail(req.getEmail(), resetUrl);
        return resetUrl;
    }

    public Student resetPassword(ResetPasswordRequest request) {
        Student student = studentRepo.findByResetToken(request.getToken())
                .orElseThrow(ApiException::invalidToken);


        if (student.getResetTokenExpiryTime().isBefore(LocalDateTime.now())) {
            throw ApiException.invalidToken();
        }

        student.setResetTokenExpiryTime(null);
        student.setResetToken(null);
        student.setPassword(passwordEncoder.encode(request.getNewPassword()));

        studentRepo.save(student);

        return student;
    }

    public AuthResponse refreshToken(String refreshToken) {
        Student student = studentRepo.findAll().stream()
                .filter(s -> refreshToken.equals(s.getRefreshToken()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (student.getRefreshTokenExpiryTime() == null || student.getRefreshTokenExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired. Please login again.");
        }

        String newAccessToken = jwtUtil.generateToken(student.getId(), student.getRole());
        String newRefreshToken = UUID.randomUUID().toString();

        student.setRefreshToken(newRefreshToken);
        student.setRefreshTokenExpiryTime(LocalDateTime.now().plusDays(7));
        studentRepo.save(student);

        return new AuthResponse(newAccessToken, newRefreshToken, student.getRole());
    }

}

