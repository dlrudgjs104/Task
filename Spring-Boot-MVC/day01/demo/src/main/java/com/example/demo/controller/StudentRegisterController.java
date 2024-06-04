package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRegisterRequest;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {
    private final StudentRepository studentRepository;

    public StudentRegisterController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public String studentRegisterForm() {
        return "studentRegister";
    }

    @PostMapping
    public ModelAndView registerStudent(@ModelAttribute StudentRegisterRequest studentRequest) {
        Student student = studentRepository.addStudent(studentRequest.getId(), studentRequest.getPassword(), studentRequest.getName(), studentRequest.getEmail(), studentRequest.getScore(), studentRequest.getEvaluation());

        ModelAndView mav = new ModelAndView("studentView");
        mav.addObject("student", Student.constructPasswordMaskedStudent(student));

        return mav;
    }
}
