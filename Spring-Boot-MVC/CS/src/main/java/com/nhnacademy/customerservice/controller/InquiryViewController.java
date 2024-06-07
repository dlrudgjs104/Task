package com.nhnacademy.customerservice.controller;

import com.nhnacademy.customerservice.domain.Inquiry;
import com.nhnacademy.customerservice.repository.InquiryRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
public class InquiryViewController {
    private final InquiryRepository inquiryRepository;

    public InquiryViewController(InquiryRepository inquiryRepository) {
        this.inquiryRepository = inquiryRepository;
    }

    @GetMapping("/cs")
    public String userInquiryViewGet(@RequestParam(value = "categoryName", required = false) String categoryName,
                                     Model model,
                                     HttpSession session) {
        String userId = (String) session.getAttribute("userId");
        List<Inquiry> inquiries = Objects.isNull(categoryName) || categoryName.equals("all") ? inquiryRepository.getInquiriesByUser(userId) : inquiryRepository.getInquiriesByCategory(userId, categoryName);

        model.addAttribute("inquiries", inquiries);

        return "userPage";
    }

    @GetMapping("/cs/admin")
    public String adminInquiryViewGet(Model model) {
        List<Inquiry> inquiries = inquiryRepository.getInquiriesByNotAnswer();

        model.addAttribute("inquiries", inquiries);

        return "adminPage";
    }
}
