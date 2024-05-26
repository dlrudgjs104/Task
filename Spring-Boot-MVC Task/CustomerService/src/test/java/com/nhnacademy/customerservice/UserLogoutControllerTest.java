package com.nhnacademy.customerservice;

import com.nhnacademy.customerservice.controller.UserLoginController;
import com.nhnacademy.customerservice.controller.UserLogoutController;
import com.nhnacademy.customerservice.repository.UserRepository;
import com.nhnacademy.customerservice.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserLogoutController.class)
@ContextConfiguration(classes = UserRepositoryImpl.class)
public class UserLogoutControllerTest {
    private MockMvc mockMvc;
    private UserRepository userRepository;
    private MockHttpSession session;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepositoryImpl.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserLogoutController()).build();
        session = new MockHttpSession();
    }

    @Test
    public void testLogoutGet() throws Exception {
        session.setAttribute("userId", "tom");

        mockMvc.perform(get("/cs/logout").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/login"))
                .andExpect(result -> {
                    MockHttpSession invalidatedSession = (MockHttpSession) result.getRequest().getSession(false);
                    assert invalidatedSession == null;
                });
    }
}
