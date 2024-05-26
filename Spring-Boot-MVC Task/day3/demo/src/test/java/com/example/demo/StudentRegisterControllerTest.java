package com.example.demo;

import com.example.demo.controller.StudentRegisterController;
import com.example.demo.domain.Student;
import com.example.demo.domain.StudentRegisterRequest;
import com.example.demo.exception.ValidationFailedException;
import com.example.demo.repository.StudentRepository;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentRegisterController.class)
class StudentRegisterControllerTest {

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
    void testGetRegisterForm() throws Exception {
        mockMvc.perform(get("/student/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("studentRegister"));
    }

    @Test
    void testRegisterStudentSuccess() throws Exception {
        String studentId = "12345";
        StudentRegisterRequest request = new StudentRegisterRequest(studentId, "password", "John Doe", "john.doe@example.com", 90, "Good student");

        when(studentRepository.addStudent(anyString(), anyString(), anyString(), anyString(), anyInt(), anyString()))
                .thenReturn(new Student(studentId, request.getPassword(), request.getName(), request.getEmail(), request.getScore(), request.getEvaluation()));

        mockMvc.perform(post("/student/register")
                        .session(session)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", request.getId())
                        .param("password", request.getPassword())
                        .param("name", request.getName())
                        .param("email", request.getEmail())
                        .param("score", String.valueOf(request.getScore()))
                        .param("evaluation", request.getEvaluation()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/student"))
                .andExpect(request().sessionAttribute("studentId", studentId));
    }

    @Test
    void testRegisterStudentValidationFailure() throws Exception {
        mockMvc.perform(post("/student/register")
                        .session(session)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "")
                        .param("password", "")
                        .param("name", "")
                        .param("email", "invalidemail")
                        .param("score", "-1")
                        .param("evaluation", ""))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ValidationFailedException));
    }
}
