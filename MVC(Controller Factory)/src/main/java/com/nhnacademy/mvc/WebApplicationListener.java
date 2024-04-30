package com.nhnacademy.mvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Random;

@WebListener
public class WebApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        StudentRepository studentRepository = new JsonStudentRepository();

        Random random = new Random();
        for(int i=1; i<=10; i++){
            Student student  = new Student("student" + String.valueOf(i), "아카데미" + String.valueOf(i), Gender.M, random.nextInt(10)+ 20);
            // ... student 1 ~ 10 생성하기
            // 나이 : random 처리 : 20~30

            studentRepository.save(student);
        }
        // ... application scope에서 studentRepository 객체에 접근할 수 있도록 구현하기
        context.setAttribute("studentRepository", studentRepository);

    }
}