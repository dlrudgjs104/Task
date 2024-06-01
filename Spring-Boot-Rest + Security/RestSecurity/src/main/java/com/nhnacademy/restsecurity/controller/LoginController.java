package com.nhnacademy.restsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth")
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login/success")
    public String loginSuccess() {
        return "success";
    }

    @GetMapping("/login/fail")
    public String loginFail() {
        return "fail";
    }
}
