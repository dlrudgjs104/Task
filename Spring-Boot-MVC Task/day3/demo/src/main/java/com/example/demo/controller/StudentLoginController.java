package com.example.demo.controller;

import com.example.demo.domain.Student;
import com.example.demo.repository.StudentRepository;
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
public class StudentLoginController {
    private final StudentRepository studentRepository;

    public StudentLoginController(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @GetMapping("/login")
    public String login(@CookieValue(value = "SESSION", required = false) String sessionId, ModelMap modelMap, HttpSession session) {
        if (StringUtils.hasText(sessionId)) {
            String studentId = session.getAttribute("studentId") != null ? (String) session.getAttribute("studentId") : null;
            if(studentId == null){
                return "loginForm";
            }

            Student student = studentRepository.getStudent(studentId);
            modelMap.put("student", student);

            return "redirect:/student";
        } else {
            return "loginForm";
        }
    }

    @PostMapping("/login")
    public String doLogin(@RequestParam("id") String studentId,
                                @RequestParam("password") String studentPassword,
                                HttpSession session,
                                HttpServletResponse response,
                                Model model) {
        if (studentRepository.matches(studentId, studentPassword)) {
            Cookie cookie = new Cookie("SESSION", session.getId());
            cookie.setMaxAge(60 * 10);  // 로그인 만료시간 10분
            response.addCookie(cookie);
            session.setAttribute("studentId", studentId);
            session.setMaxInactiveInterval(60 * 10);

            Student student = studentRepository.getStudent(studentId); // 학생 정보를 가져옴
            model.addAttribute("student", student);

            return "redirect:/student";
        } else {
            return "redirect:/login";
        }
    }

}
