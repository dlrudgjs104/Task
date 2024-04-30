package com.nhnacademy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/student/register.do", method = RequestMapping.Method.GET)
public class StudentRegisterFormController implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //todo  /student/register.jsp forward 합니다.
        return "/student/register.jsp";

    }
}
