package com.nhnacademy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StudentUpdateFormController implements Command{

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");

        //todo 학생조회
        String id = req.getParameter("id");
        Student student = studentRepository.getStudentById(id);

        //todo forward : /student/register.jsp
        if(student != null){
            req.setAttribute("isMaleChecked", student.getGender() == Gender.M);
            req.setAttribute("isFemaleChecked", student.getGender() == Gender.F);

            req.setAttribute("student", student);

            return "/student/register.jsp";
        }
        else{
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "해당 학생을 찾을 수 없음");
        }

        return null;
    }
}
