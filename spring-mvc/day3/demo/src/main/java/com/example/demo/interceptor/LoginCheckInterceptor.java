package com.example.demo.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String sessoinId = null;
        if(request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
                if("SESSION".equals(cookie.getName())) {
                    sessoinId = cookie.getValue();
                    break;
                }
            }
        }

        if(sessoinId == null || sessoinId.isEmpty()) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }

            response.sendRedirect("/login");
            return false;
        }
        return true;
    }

}
