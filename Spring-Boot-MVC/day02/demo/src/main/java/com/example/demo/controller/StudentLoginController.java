package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StudentLoginController {
    private final StudentRepository studentRepository;

    public StudentLoginController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/login")
    public String login(@CookieValue(value = "SESSION", required = false) String sessionId, ModelMap modelMap, HttpServletRequest request) {
        if (StringUtils.hasText(sessionId)) {
            Student student = studentRepository.getStudent(sessionId);
            modelMap.put("student", student);

            return "studentView";
        } else {
            return "loginForm";
        }
    }

    @PostMapping("/login")
    public ModelAndView doLogin(@RequestParam("id") String id,
                                @RequestParam("password") String password,
                                HttpServletResponse response) {
        if (studentRepository.matches(id, password)) {
            Cookie cookie = new Cookie("SESSION", id);
            cookie.setMaxAge(60 * 10);  // 로그인 만료시간 10분
            response.addCookie(cookie);

            Student student = studentRepository.getStudent(id); // 학생 정보를 가져옴
            ModelAndView mav = new ModelAndView("studentView");
            mav.addObject("student", student);

            return mav;
        } else {
            return new ModelAndView("redirect:/login");
        }
    }

}
