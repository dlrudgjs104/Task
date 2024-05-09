package com.nhnacademy.shoppingmall.controller.auth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutController {


    //todo#13-3 로그아웃 구현
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();

        return "shop/login/login_form";
    }
}
