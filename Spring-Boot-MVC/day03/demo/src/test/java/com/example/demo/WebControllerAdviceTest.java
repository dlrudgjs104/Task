package com.example.demo;

import com.example.demo.controller.StudentRegisterController;
import com.example.demo.controller.WebControllerAdvice;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {StudentRegisterController.class, WebControllerAdvice.class})
class WebControllerAdviceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private StudentRepository studentRepository;

    private MockHttpSession session;

    @BeforeEach
    void setup() {
        session = new MockHttpSession();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testHandleRuntimeException() throws Exception {
        when(studentRepository.addStudent(anyString(), anyString(), anyString(), anyString(), anyInt(), anyString()))
                .thenThrow(new RuntimeException("Unexpected error"));

        mockMvc.perform(post("/student/register")
                        .session(session)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "12345")
                        .param("password", "password")
                        .param("name", "John Doe")
                        .param("email", "john.doe@example.com")
                        .param("score", "90")
                        .param("evaluation", "Good student"))
                .andExpect(status().isOk())
                .andExpect(view().name("error"))
                .andExpect(model().attributeExists("exception"))
                .andExpect(model().attribute("exception", instanceOf(RuntimeException.class)));
    }
}
