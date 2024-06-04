package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Student {
    private final String id;
    private final String password;
    private String name;
    private String email;
    private int score;
    private String evaluation;

    public static Student create(String id, String password) {
        return new Student(id, password);
    }

    public Student(String id, String password) {
        this.id = id;
        this.password = password;
    }

    private static final String MASK = "*****";

    public static Student constructPasswordMaskedStudent(Student student){
        Student newStudent = Student.create(student.getId(), MASK);
        newStudent.setName(student.getName());
        newStudent.setEmail(student.getEmail());
        newStudent.setScore(student.getScore());
        newStudent.setEvaluation(student.getEvaluation());

        return newStudent;
    }
}
