package com.nhnacademy.customerservice;

import com.nhnacademy.customerservice.controller.InquiryAddController;
import com.nhnacademy.customerservice.controller.UserLoginController;
import com.nhnacademy.customerservice.exception.UserLoginFailedException;
import com.nhnacademy.customerservice.repository.InquiryRepository;
import com.nhnacademy.customerservice.repository.UserRepository;
import com.nhnacademy.customerservice.repository.UserRepositoryImpl;
import com.nhnacademy.customerservice.validator.InquiryAddRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserLoginController.class)
@ContextConfiguration(classes = UserRepository.class)
public class UserLoginControllerTest {
    private MockMvc mockMvc;
    private UserRepository userRepository;
    private MockHttpSession session;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepositoryImpl.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserLoginController(userRepository)).build();
        session = new MockHttpSession();
    }

    @Test
    public void testLoginForm() throws Exception {
        mockMvc.perform(get("/cs/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("loginForm"));
    }

    @Test
    public void testAlreadyLoggedInUserRedirectToUserPage() throws Exception {
        session.setAttribute("userId", "tom");

        when(userRepository.checkAdmin("user")).thenReturn(false);

        mockMvc.perform(get("/cs/login").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs"));
    }

    @Test
    public void testDoLoginSuccessUser() throws Exception {
        when(userRepository.matches("tom", "1234")).thenReturn(true);
        when(userRepository.checkAdmin("tom")).thenReturn(false);

        mockMvc.perform(post("/cs/login")
                        .param("id", "tom")
                        .param("password", "1234")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs"));
    }

    @Test
    public void testDoLoginSuccessAdmin() throws Exception {
        when(userRepository.matches("1", "1")).thenReturn(true);
        when(userRepository.checkAdmin("1")).thenReturn(true);

        mockMvc.perform(post("/cs/login")
                        .param("id", "1")
                        .param("password", "1")
                        .session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/admin"));
    }

    @Test
    public void testDoLoginFailure() throws Exception {
        when(userRepository.matches(anyString(), anyString())).thenReturn(false);

        mockMvc.perform(post("/cs/login")
                        .param("id", "testId")
                        .param("password", "testPassword"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UserLoginFailedException))
                .andExpect(result -> assertEquals("failed to login", result.getResolvedException().getMessage()));
    }
}
