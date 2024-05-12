package com.nhnacademy.shoppingmall.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = {"/", "/index.jsp", "/index.html"})
public class WelcomePageFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        //todo#9 /요청이 오면 welcome page인 index.do redirect 합니다.
        String requestURI = req.getRequestURI();
        if (requestURI.equals("/") || requestURI.equals("/index.jsp") || requestURI.equals("/index.html")) {
            res.sendRedirect(req.getContextPath() + "/index.do");
        }else{
            chain.doFilter(req, res);
        }

    }
}
