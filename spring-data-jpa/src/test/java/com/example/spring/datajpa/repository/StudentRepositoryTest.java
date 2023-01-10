package com.example.spring.datajpa.repository;

import com.example.spring.datajpa.entity.CourseMaterial;
import com.example.spring.datajpa.entity.Guardian;
import com.example.spring.datajpa.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;
    @Test
    public void saveStudent(){
        Student student=Student.builder()
                .emailId("abcd1234@gmail.com")
                .firstName("ab")
                .LastName("cd")
//                .guardianName("papa")
//                .guardianEmail("papa@gmail.com")
//                .guardianMobile("123456789")
                .build();
        studentRepository.save(student);
    }

    @Test
    public void saveStudentWithGuardian(){
        Guardian guardian = Guardian.builder()
                .email("pappukepapa@gmail.com")
                .name("pappu k papa")
                .mobile("9009099029")
                .build();

        Student student = Student.builder()
                .firstName("pappu")
                .emailId("pappu1234@gmail.com")
                .LastName("kumar d")
                .guardian(guardian)
                .build();
        studentRepository.save(student);
    }

    @Test
    public void printAllStudent(){
        List<Student> studentList = studentRepository.findAll();
        System.out.println("studentList = " + studentList);
    }

    @Test
    public void printStudentByFirstName(){
        List<Student> students = studentRepository.findByFirstName("pappu");
        System.out.println("students = " + students);
    }

    @Test
    public void printGetStudentByEmailAddress(){
        Student student = studentRepository.getStudentByEmailAddress("pappu1234@gmail.com");
        System.out.println("student = " + student);
    }

}