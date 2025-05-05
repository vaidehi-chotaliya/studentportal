package com.example.student_portal.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {
    @Getter
    @Id
    private String id;

    @Indexed(unique = true)
    private String enrollmentNo; // e.g., GTU2024001

    @Indexed(unique = true)
    private String email;

    private String fullName;

    private String password; // BCrypt hashed

    //    @Builder.Default
    private String role;

    private boolean isVerified = false;

    private String resetToken;// for reset password

    private LocalDateTime resetTokenExpiryTime; // for reset password

    private String refreshToken;
    private LocalDateTime refreshTokenExpiryTime;

    private LocalDateTime createdAt;
}

