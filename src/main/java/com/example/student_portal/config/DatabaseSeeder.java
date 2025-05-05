package com.example.student_portal.config;

import com.example.student_portal.entity.Admin;
import com.example.student_portal.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = Admin.builder()
                    .email("admin@studentportal.com")
                    .fullName("Super Admin")
                    .password(passwordEncoder.encode("Admin@123")) // Hash the password
                    .isVerified(true)
                    .createdAt(LocalDateTime.now())
                    .build();

            adminRepository.save(admin);
            System.out.println("✅ Admin user created successfully.");
        } else {
            System.out.println("✅ Admin user already exists. Skipping seeder...");
        }
    }
}
