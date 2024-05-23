package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRegisterRequest;
import com.example.demo.exception.ValidationFailedException;
import com.example.demo.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
    public String registerStudent(@Valid @ModelAttribute StudentRegisterRequest studentRequest,
                                        BindingResult bindingResult,
                                        HttpSession session) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Student student = studentRepository.addStudent(studentRequest.getId(), studentRequest.getPassword(), studentRequest.getName(), studentRequest.getEmail(), studentRequest.getScore(), studentRequest.getEvaluation());
        session.setAttribute("studentId", studentRequest.getId());
        ModelAndView mav = new ModelAndView("studentView");
        mav.addObject("student", Student.constructPasswordMaskedStudent(student));

        return "redirect:/student";
    }
}
