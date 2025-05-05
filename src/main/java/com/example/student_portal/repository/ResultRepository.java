package com.example.student_portal.repository;

import com.example.student_portal.entity.Admin;
import com.example.student_portal.entity.Result;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ResultRepository extends MongoRepository<Result, String> {
    List<Result> findByStudentId(String studentId);

    List<Result> findBySemester(int semester);

    List<Result> findByStudentIdAndSemester(String studentId, int semester);

    @Query(value = "{ 'studentId': ?0 }", sort = "{ 'semester': -1 }")
    List<Result> findTop8ByStudentIdOrderBySemesterDesc(String studentId);
}


