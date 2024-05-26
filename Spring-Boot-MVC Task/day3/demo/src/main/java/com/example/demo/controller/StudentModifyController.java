package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRegisterRequest;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.exception.ValidationFailedException;
import com.example.demo.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentModifyController {

    private final StudentRepository studentRepository;

    public StudentModifyController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/student/modify")
    public String getModifyForm(HttpSession session, Model model) {
        String studentId = session.getAttribute("studentId").toString();
        Student student = studentRepository.getStudent(studentId);
        model.addAttribute("student", student);
        return "studentModify";
    }

    @PostMapping("/student/modify")
    public String postModifyForm(@Valid @ModelAttribute StudentRegisterRequest studentRequest,
                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        Student student = studentRepository.updateStudent(studentRequest.getId(), studentRequest.getPassword(), studentRequest.getName(), studentRequest.getEmail(), studentRequest.getScore(), studentRequest.getEvaluation());

        ModelAndView mav = new ModelAndView("redirect:/student");
        mav.addObject("student", Student.constructPasswordMaskedStudent(student));

        return "redirect:/student";
    }

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void notFound(){

    }
}
