package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRegisterRequest;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentModifyController {

    private final StudentRepository studentRepository;

    public StudentModifyController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    @GetMapping("/student/{studentId}/modify")
    public String getModifyForm(Model model,
                                 @PathVariable("studentId") String id) {
        Student student = studentRepository.getStudent(id);
        model.addAttribute("student", student);
        return "studentModify";
    }

    @PostMapping("/student/modify")
    public ModelAndView postModifyForm(@ModelAttribute StudentRegisterRequest studentRequest) {
        Student student = studentRepository.updateStudent(studentRequest.getId(), studentRequest.getPassword(), studentRequest.getName(), studentRequest.getEmail(), studentRequest.getScore(), studentRequest.getEvaluation());

        ModelAndView mav = new ModelAndView("redirect:/student/" + student.getId());
        mav.addObject("student", Student.constructPasswordMaskedStudent(student));

        return mav;

    }
}
