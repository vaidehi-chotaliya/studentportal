package com.example.student_portal.repository;

import com.example.student_portal.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student , String> {

    Optional<Student> findByEmail(String email);
    Optional<Student> findByEmailOrEnrollmentNo(String email, String enrollmentNo);
    boolean existsByEmail(String email);

    Optional<Student> findByResetToken(String resetToken);



}
