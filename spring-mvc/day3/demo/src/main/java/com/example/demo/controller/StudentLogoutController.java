package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentLogoutController {

    @GetMapping("/logout")
    public String login(@CookieValue(value = "SESSION", required = false) String sessionId, HttpServletResponse response, HttpSession session) {
        if (StringUtils.hasText(sessionId)) {
            Cookie cookie = new Cookie("SESSION", null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            if (session.getAttribute("studentId") != null) {
                session.invalidate();
            }
        }

        return "redirect:/login";
    }

}
