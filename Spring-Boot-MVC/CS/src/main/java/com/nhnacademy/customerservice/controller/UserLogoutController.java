package com.nhnacademy.customerservice.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLogoutController {

    @GetMapping("/cs/logout")
    public String logout(HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        if (StringUtils.hasText(userId)) {
            session.invalidate();
        }

        return "redirect:/cs/login";
    }

}
