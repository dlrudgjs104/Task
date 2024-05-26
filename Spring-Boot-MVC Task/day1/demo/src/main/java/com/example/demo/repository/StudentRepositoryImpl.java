package com.example.demo.repository;

import com.example.demo.domain.Student;
import com.example.demo.exception.StudentAlreadyExistsException;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final Map<String, Student> StudentMap = new HashMap<>();

    public StudentRepositoryImpl() {
        StudentMap.put("1", Student.create("1", "1"));
        StudentMap.put("tom", new Student("tom", "1234", "Tom", "tom@naver.com", 100,"Excellent"));
        StudentMap.put("jake", new Student("jake", "567fgse", "Jake", "jake@naver.com", 82,"good"));
        StudentMap.put("ethan", new Student("ethan", "sdf3@#4", "Ethan", "ethan@naver.com", 20,"bad"));
    }

    @Override
    public boolean exists(String id) {
        return StudentMap.containsKey(id);
    }

    @Override
    public boolean matches(String id, String password) {
        return Optional.ofNullable(getStudent(id))
                .map(Student -> Student.getPassword().equals(password))
                .orElse(false);
    }

    @Override
    public List<Student> getStudents() {
        return StudentMap.values().stream().toList();
    }

    @Override
    public Student getStudent(String id) {
        return StudentMap.get(id);
    }

    @Override
    public Student addStudent(String id, String password) {
        return addStudent(id, password, "", "", 0, "");
    }

    @Override
    public Student addStudent(String id, String password, String name, String email, int score, String evaluation) {
        if (exists(id)) {
            throw new StudentAlreadyExistsException();
        }

        Student student = Student.create(id, password);

        student.setName(name);
        student.setEmail(email);
        student.setScore(score);
        student.setEvaluation(evaluation);

        StudentMap.put(id, student);

        return student;
    }

    @Override
    public Student updateStudent(String id, String password, String name, String email, int score, String evaluation) {
        if(!Objects.isNull(id) || !Objects.isNull(password) || !Objects.isNull(name) || !Objects.isNull(email) || !Objects.isNull(score) || !Objects.isNull(evaluation)){
            Student student = new Student(id, password, name, email, score, evaluation);

            StudentMap.put(id, student);

            return student;
        }
        return null;
    }
}
