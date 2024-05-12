package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/loginAction.do")
public class LoginPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.
        String userId = req.getParameter("user_id");
        String password = req.getParameter("user_password");
        try
        {
            if(userService.doLogin(userId, password) != null){
                req.getSession().setAttribute("user_id", userId);
                req.getSession().setMaxInactiveInterval(60 * 60);
                if(userService.getAuth(userId).toString().equals("ROLE_ADMIN")){
                    req.getSession().setAttribute("role", "ROLE_ADMIN");
                } else {
                    req.getSession().setAttribute("role", "ROLE_USER");
                }

                return "redirect:/index.do";
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
        }
        return "shop/login/login_form";
    }
}
