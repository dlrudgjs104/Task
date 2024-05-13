package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/signupAction.do")
public class SignupPostController implements BaseController {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String userName = req.getParameter("user_name");
        String userPassword = req.getParameter("user_password");
        String userBirth = req.getParameter("user_birth");
        User.Auth userAuth = User.Auth.ROLE_USER;
        int userPoint = 1_000_000;
        LocalDateTime now = LocalDateTime.now();

        User user = new User(userId, userName, userPassword, userBirth, userAuth, userPoint, now, null);
        log.debug("user: {}", user);

        try {
            userService.saveUser(user);
            req.setAttribute("signupMessage", "가입에 성공하였습니다.");
            return "shop/signup/signup_form";
        } catch (Exception e) {
            log.debug(e.getMessage());
            req.setAttribute("signupMessage", "가입에 실패하였습니다.");
            return "shop/signup/signup_form";
        }
    }
}
