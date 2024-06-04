package com.nhnacademy.customerservice;

import com.nhnacademy.customerservice.controller.InquiryViewController;
import com.nhnacademy.customerservice.domain.Inquiry;
import com.nhnacademy.customerservice.repository.InquiryRepository;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InquiryViewController.class)
@ContextConfiguration(classes = {UserRepositoryImpl.class, InquiryRepository.class})
public class InquiryViewControllerTest {
    private MockMvc mockMvc;
    private UserRepository userRepository;
    private InquiryRepository inquiryRepository;

    @BeforeEach
    void setUp(){
        userRepository  = mock(UserRepositoryImpl.class);
        inquiryRepository = mock(InquiryRepository.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new InquiryViewController(inquiryRepository)).build();
    }

    @Test
    public void testUserInquiryViewGetWithCategory() throws Exception {
        Inquiry mockInquiry = new Inquiry();
        when(inquiryRepository.getInquiriesByUser(anyString())).thenReturn(Collections.singletonList(mockInquiry));
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", "tom");

        // 전체 카테고리
        mockMvc.perform(get("/cs").param("categoryName", "all").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(model().attribute("inquiries", org.hamcrest.Matchers.hasSize(1)));

        // 불만 접수 카테고리
        mockMvc.perform(get("/cs").param("categoryName", "불만 접수").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(model().attribute("inquiries", org.hamcrest.Matchers.hasSize(0)));

        // 제안 카테고리
        mockMvc.perform(get("/cs").param("categoryName", "제안").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(model().attribute("inquiries", org.hamcrest.Matchers.hasSize(0)));

        // 환불/교환 카테고리
        mockMvc.perform(get("/cs").param("categoryName", "환불/교환").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(model().attribute("inquiries", org.hamcrest.Matchers.hasSize(0)));

        // 칭찬해요 카테고리
        mockMvc.perform(get("/cs").param("categoryName", "칭찬해요").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(model().attribute("inquiries", org.hamcrest.Matchers.hasSize(0)));

        // 기타 문의 카테고리
        mockMvc.perform(get("/cs").param("categoryName", "기타 문의").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("userPage"))
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(model().attribute("inquiries", org.hamcrest.Matchers.hasSize(0)));
    }

    @Test
    public void testAdminInquiryViewGet() throws Exception {
        List<Inquiry> mockInquiry = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        mockInquiry.add(new Inquiry(1, "불만 접수", "상품 불량", "구매한 사과가 썩어있어요.", now, "tom", new ArrayList<>(List.of("/static/image/1_1.jpeg"))));
        mockInquiry.add(new Inquiry(2, "제안", "상품 추가 요청", "다양한 종류의 과일을 추가해 주세요.", now, "고객2", new ArrayList<>(List.of("/static/image/2_1.png", "/static/image/2_2.png"))));
        mockInquiry.add(new Inquiry(3, "환불/교환", "환불 요청", "구매한 바나나를 환불하고 싶습니다.", now, "고객3", new ArrayList<>(List.of("/static/image/3_1.png"))));
        mockInquiry.add(new Inquiry(4, "칭찬해요", "좋은 서비스", "직원들이 매우 친절했어요.", now, "고객4", new ArrayList<>(List.of("/static/image/4_1.jpeg"))));
        mockInquiry.add(new Inquiry(5, "기타 문의", "영업 시간", "주말에도 영업하시나요?", now, "고객5", new ArrayList<>(List.of("/static/image/5_1.png"))));

        when(inquiryRepository.getInquiriesByNotAnswer()).thenReturn(mockInquiry);
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("userId", "tom");

        mockMvc.perform(get("/cs/admin").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("adminPage"))
                .andExpect(model().attributeExists("inquiries"))
                .andExpect(model().attribute("inquiries", org.hamcrest.Matchers.hasSize(5)));
    }


}
