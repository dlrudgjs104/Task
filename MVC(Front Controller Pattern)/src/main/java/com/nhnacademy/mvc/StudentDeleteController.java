package com.nhnacademy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StudentDeleteController implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");
        String id = req.getParameter("id");

        if(id == null || id.isEmpty()){
            throw new RuntimeException("id is null");
        }
        else{
            studentRepository.deleteById(id);
        }

        //view를 return 합니다.
        return "redirect:/student/list.do";
    }
}