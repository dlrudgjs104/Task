package com.example.demo.repository;


import com.example.demo.domain.Student;

import java.util.List;

public interface StudentRepository {
    boolean exists(String id);

    boolean matches(String id, String password);

    List<Student> getStudents();

    Student getStudent(String id);

    Student addStudent(String id, String password, String name, String email, int score, String evaluation);

    Student updateStudent(String id, String newPassword, String newName, String newMail, int newScore, String newEvaluation);
}
