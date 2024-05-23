package com.example.demo;

import com.example.demo.controller.StudentLogoutController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentLogoutController.class)
class StudentLogoutControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    private MockHttpSession session;

    @BeforeEach
    void setup() {
        session = new MockHttpSession();
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testLogoutWithoutSession() throws Exception {
        mockMvc.perform(get("/logout"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(cookie().doesNotExist("SESSION"));
    }

    @Test
    void testLogoutWithSession() throws Exception {
        session.setAttribute("studentId", "12345");
        Cookie sessionCookie = new Cookie("SESSION", session.getId());

        mockMvc.perform(get("/logout").session(session).cookie(sessionCookie))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"))
                .andExpect(cookie().maxAge("SESSION", 0));
    }
}
