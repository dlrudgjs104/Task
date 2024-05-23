package com.example.demo;

import com.example.demo.controller.StudentLoginController;
import com.example.demo.domain.Student;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.Cookie;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentLoginController.class)
class StudentLoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private WebApplicationContext context;

    private MockHttpSession session;

    @BeforeEach
    void setup() {
        session = new MockHttpSession();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testGetLoginPage() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }

    @Test
    void testGetLoginPageWithSession() throws Exception {
        session.setAttribute("studentId", "12345");
        when(studentRepository.getStudent("12345")).thenReturn(new Student("12345", "John Doe"));

        mockMvc.perform(get("/login").session(session).cookie(new Cookie("SESSION", session.getId())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student"));
    }

    @Test
    void testPostLoginSuccess() throws Exception {
        String studentId = "12345";
        String password = "password";
        when(studentRepository.matches(studentId, password)).thenReturn(true);
        when(studentRepository.getStudent(studentId)).thenReturn(new Student(studentId, "John Doe"));

        mockMvc.perform(post("/login")
                        .param("id", studentId)
                        .param("password", password)
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student"))
                .andExpect(cookie().value("SESSION", session.getId()));
    }

    @Test
    void testPostLoginFailure() throws Exception {
        String studentId = "12345";
        String password = "wrongpassword";
        when(studentRepository.matches(studentId, password)).thenReturn(false);

        mockMvc.perform(post("/login")
                        .param("id", studentId)
                        .param("password", password)
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }
}
