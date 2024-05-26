package com.nhnacademy.customerservice;

import com.nhnacademy.customerservice.controller.AnswerAddController;
import com.nhnacademy.customerservice.controller.InquiryAddController;
import com.nhnacademy.customerservice.domain.AnswerAddRequest;
import com.nhnacademy.customerservice.domain.Inquiry;
import com.nhnacademy.customerservice.domain.User;
import com.nhnacademy.customerservice.repository.InquiryRepository;
import com.nhnacademy.customerservice.repository.InquiryRepositoryImpl;
import com.nhnacademy.customerservice.repository.UserRepository;
import com.nhnacademy.customerservice.repository.UserRepositoryImpl;
import com.nhnacademy.customerservice.validator.AnswerAddRequestValidator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InquiryAddController.class)
@ContextConfiguration(classes = InquiryRepository.class)
public class AnswerAddControllerTest {
    private MockMvc mockMvc;
    private UserRepository userRepository;
    private InquiryRepository inquiryRepository;
    private AnswerAddRequestValidator validator;

    @BeforeEach
    void setUp(){
        userRepository = mock(UserRepositoryImpl.class);
        inquiryRepository = mock(InquiryRepositoryImpl.class);
        validator = mock(AnswerAddRequestValidator.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new AnswerAddController(userRepository, inquiryRepository, validator)).build();
    }

    @Test
    public void testSupports() {
        // 지원하는 클래스인지 확인
        final AnswerAddRequestValidator validator = new AnswerAddRequestValidator();
        assertTrue(validator.supports(AnswerAddRequest.class));
    }

    @Test
    public void testAddAnswerGet() throws Exception {
        Inquiry mockInquiry = new Inquiry();
        mockInquiry.setId(1);
        when(inquiryRepository.getInquiry("1")).thenReturn(mockInquiry);

        mockMvc.perform(MockMvcRequestBuilders.get("/cs/admin/answer?inquiryId=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("answerAddForm"));
    }

    @Test
    public void testAddAnswerPost() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", "1");

        when(userRepository.getUser("1")).thenReturn(new User("1", "1", "1", User.Auth.AUTH_ADMIN));

        MockHttpServletRequestBuilder answerRequest = MockMvcRequestBuilders.post("/cs/admin/answer")
                .param("id", "1")
                .param("answerTitle", "TestTitle")
                .param("answerCreatedBy", "1")
                .session(session);

        mockMvc.perform(answerRequest)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs/admin"));
    }
}
