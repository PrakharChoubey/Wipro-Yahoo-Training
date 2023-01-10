package com.example.spring.datajpa.repository;

import com.example.spring.datajpa.entity.Course;
import com.example.spring.datajpa.entity.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CourseMaterialRepositoryTest {
    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Test
    public void saveCourseMaterial(){
        Course course = Course.builder()
                .title("DSA").credit(6)
                .build();

        CourseMaterial courseMaterial = CourseMaterial.builder()
                .url("www.amazon.com")
                .course(course)
                .build();
        courseMaterialRepository.save(courseMaterial);
    }


    @Test
    public void printAllCourseMaterials(){
        List<CourseMaterial> courseMaterials = courseMaterialRepository.findAll();
        System.out.println("courseMaterials = " + courseMaterials);
    }
}