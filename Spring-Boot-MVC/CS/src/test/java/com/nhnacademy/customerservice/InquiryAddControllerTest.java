package com.nhnacademy.customerservice;

import com.nhnacademy.customerservice.controller.InquiryAddController;
import com.nhnacademy.customerservice.domain.Inquiry;
import com.nhnacademy.customerservice.domain.InquiryAddRequest;
import com.nhnacademy.customerservice.repository.InquiryRepository;
import com.nhnacademy.customerservice.validator.InquiryAddRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(InquiryAddController.class)
@ContextConfiguration(classes = InquiryRepository.class)
public class InquiryAddControllerTest {
    private MockMvc mockMvc;
    private InquiryRepository inquiryRepository;
    private InquiryAddRequestValidator validator;

    @BeforeEach
    void setUp(){
        inquiryRepository = mock(InquiryRepository.class);
        validator = mock(InquiryAddRequestValidator.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new InquiryAddController(inquiryRepository, validator)).build();
    }

    @Test
    public void testSupports() {
        // 지원하는 클래스인지 확인
        final InquiryAddRequestValidator validator = new InquiryAddRequestValidator();
        assertTrue(validator.supports(InquiryAddRequest.class));
    }

    @Test
    public void testAddInquiryGetWithNullId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/cs/inquiry"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("inquiryAddForm"));
    }

    @Test
    public void testAddInquiryGetWithId() throws Exception {
        Inquiry mockInquiry = new Inquiry();
        mockInquiry.setId(1);
        when(inquiryRepository.getInquiry("1")).thenReturn(mockInquiry);

        mockMvc.perform(MockMvcRequestBuilders.get("/cs/inquiry?id=1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("inquiryAnswerForm"));
    }

    @Test
    public void testAddInquiryPostWithNullFiles() throws Exception {
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", "tom");

        MockHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders.multipart("/cs/inquiry")
                .param("category", "불만 접수")
                .param("title", "TestTitle")
                .param("content", "TestContent")
                .session(session);

        mockMvc.perform(multipartRequest)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs"));
    }

    @Test
    public void testAddInquiryPostWithFiles() throws Exception {
        MockHttpSession session = new MockHttpSession();
        MockMultipartFile file = new MockMultipartFile("files", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "image data".getBytes());
        session.setAttribute("userId", "tom");


        MockHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders.multipart("/cs/inquiry")
                .session(session)
                .param("category", "불만 접수")
                .param("title", "TestTitle")
                .param("content", "TestContent");

        mockMvc.perform(multipartRequest)
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/cs"));
    }

}
