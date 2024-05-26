package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/student")
    public String getStudentHideScore(@RequestParam(value = "hideScore", required = false) String hideScore , HttpSession session, Model model) {
        String studentId = (String) session.getAttribute("studentId");
        Student student = studentRepository.getStudent(studentId);
        model.addAttribute("student", student);

        return !Objects.isNull(hideScore) && hideScore.equals("yes") ? "studentViewHideScore" : "studentView";
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound(){

    }
}
