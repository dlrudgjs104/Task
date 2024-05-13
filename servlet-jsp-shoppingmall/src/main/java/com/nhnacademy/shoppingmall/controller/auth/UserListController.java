package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;

import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = "/userList.do")
public class UserListController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = new UserServiceImpl(new UserRepositoryImpl());
        List<User> userList = userService.findAllUser();

        req.setAttribute("listKind", "user");
        req.setAttribute("userList", userList);
        return "shop/page/adminpage_form";
    }
}
