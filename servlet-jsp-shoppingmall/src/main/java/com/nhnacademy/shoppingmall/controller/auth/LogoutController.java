package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET,value = "/logout.do")
public class LogoutController implements BaseController {
    //todo#13-3 로그아웃 구현
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().invalidate();
        String user_id = req.getParameter("user_id");
        log.debug("user_id: {} 로그아웃", user_id);
        return "redirect:/index.do";
    }
}
