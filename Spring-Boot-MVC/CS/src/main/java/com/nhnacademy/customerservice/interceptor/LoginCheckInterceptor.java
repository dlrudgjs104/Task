package com.nhnacademy.customerservice.interceptor;

import com.nhnacademy.customerservice.domain.User;
import com.nhnacademy.customerservice.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;

    public LoginCheckInterceptor(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userId = (String) request.getSession().getAttribute("userId");
        User.Auth userRole;

        if(userId == null) {
            response.sendRedirect("/cs/login");
            return false;
        } else {
            userRole = userRepository.getUser(userId).getAuth();
        }

        if (request.getRequestURI().startsWith("/cs/admin") && userRole != User.Auth.AUTH_ADMIN) {
            response.sendRedirect("/cs/login");
            return false;
        }

        return true;
    }

}
