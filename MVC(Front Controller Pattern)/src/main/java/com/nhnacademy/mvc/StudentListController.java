package com.nhnacademy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class StudentListController implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");
        //student list 구하기
        List<Student> studentList = studentRepository.getStudents();

        req.setAttribute("studentList",studentList);

        req.setAttribute("view", "/student/list.jsp");

        return "/student/list.jsp";
    }
}

