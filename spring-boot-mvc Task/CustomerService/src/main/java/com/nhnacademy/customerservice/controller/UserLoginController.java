package com.nhnacademy.customerservice.controller;

import com.nhnacademy.customerservice.domain.User;
import com.nhnacademy.customerservice.exception.UserLoginFailedException;
import com.nhnacademy.customerservice.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserLoginController {
    private final UserRepository userRepository;

    public UserLoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/cs/login")
    public String login(Model model, HttpSession session) {
        String userId = (String) session.getAttribute("userId");

        if (StringUtils.hasText(userId)) {
            return userRepository.checkAdmin(userId) ? "redirect:/cs/admin" : "redirect:/cs";

        } else {
            return "loginForm";
        }
    }

    @PostMapping("/cs/login")
    public String doLogin(@RequestParam("id") String userId,
                                @RequestParam("password") String userPassword,
                                HttpSession session,
                                Model model) {
        if (userRepository.matches(userId, userPassword)) {
            session.setAttribute("userId", userId);
            session.setMaxInactiveInterval(60 * 10);

            return userRepository.checkAdmin(userId) ? "redirect:/cs/admin" : "redirect:/cs";

        } else {
            throw new UserLoginFailedException("failed to login");
        }
    }

}
