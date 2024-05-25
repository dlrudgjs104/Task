package com.nhnacademy.customerservice.controller;

import com.nhnacademy.customerservice.domain.Inquiry;
import com.nhnacademy.customerservice.domain.AnswerAddRequest;
import com.nhnacademy.customerservice.exception.ValidationFailedException;
import com.nhnacademy.customerservice.repository.InquiryRepository;
import com.nhnacademy.customerservice.repository.UserRepository;
import com.nhnacademy.customerservice.validator.InquiryAnswerRequestValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class AnswerAddController {
    private final UserRepository userRepository;
    private final InquiryRepository inquiryRepository;
    private final InquiryAnswerRequestValidator validator;


    public AnswerAddController(UserRepository userRepository, InquiryRepository inquiryRepository, InquiryAnswerRequestValidator validator) {
        this.userRepository = userRepository;
        this.inquiryRepository = inquiryRepository;
        this.validator = validator;
    }

    @GetMapping("/cs/admin/answer")
    public String addAnswerGet(@RequestParam("inquiryId") String inquiryId, Model model) {
        Inquiry inquiry = inquiryRepository.getInquiry(inquiryId);
        model.addAttribute("inquiry", inquiry);

        return "answerAddForm";
    }

    @PostMapping("/cs/admin/answer")
    public String addAnswerPost(@Validated @ModelAttribute AnswerAddRequest req,
                                BindingResult bindingResult,
                                HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        String userName = userRepository.getUser(userId).getName();

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }


        inquiryRepository.answerToInquiry(req.getId(), req.getAnswerContent(), userName);
        return "redirect:/cs/admin";
    }

    @InitBinder("answerAddRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
}
