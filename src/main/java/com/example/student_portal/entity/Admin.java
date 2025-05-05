package com.example.student_portal.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String fullName;

    private String password; // Bcrypt hashed

    private boolean isVerified = true; // Optional

    private LocalDateTime createdAt;
}
