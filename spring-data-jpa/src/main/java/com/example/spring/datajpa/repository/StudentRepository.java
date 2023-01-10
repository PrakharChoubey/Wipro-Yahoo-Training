package com.example.spring.datajpa.repository;

import com.example.spring.datajpa.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {

    public List<Student> findByFirstName(String firstName);

    @Query("Select s from Student s where s.emailId = ?1")
    Student getStudentByEmailAddress(String emailId);
}
