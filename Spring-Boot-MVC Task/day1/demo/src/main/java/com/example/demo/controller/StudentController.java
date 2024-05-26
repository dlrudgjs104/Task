package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class StudentController {
    private final StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository) {this.studentRepository = studentRepository;}

    @GetMapping("/students")
    public String getStudents(Model model) {
        List<Student> students = studentRepository.getStudents();
        model.addAttribute("students", students);
        return "students";
    }

    @GetMapping("/student/{studentId}")
    public String getStudentHideScore(@PathVariable("studentId") String studentId,
                                      @RequestParam(value = "hideScore", required = false) String hideScore ,
                                      Model model) {
        Student student = studentRepository.getStudent(studentId);
        model.addAttribute("student", student);

        return !Objects.isNull(hideScore) && hideScore.equals("yes") ? "studentViewHideScore" : "studentView";
    }
}
