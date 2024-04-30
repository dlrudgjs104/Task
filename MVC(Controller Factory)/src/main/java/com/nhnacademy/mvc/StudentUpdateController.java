package com.nhnacademy.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "/student/update.do", method = RequestMapping.Method.POST)
public class StudentUpdateController implements Command{
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        StudentRepository studentRepository = (StudentRepository) req.getServletContext().getAttribute("studentRepository");

        //todo null check
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        Gender gender = Gender.valueOf(req.getParameter("gender"));
        int age = Integer.parseInt(req.getParameter("age"));

        if (id == null || name == null || gender == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid student data");
            return "";
        }

        //todo student 저장
        Student student = new Student(id, name, gender, age);
        studentRepository.update(student);

        //todo /student/view?id=student1 <-- redirect
        return "redirect:/student/view.do?id=" + id;
    }
}
