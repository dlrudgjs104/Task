package com.nhnacademy.customerservice.repository;

import com.nhnacademy.customerservice.domain.Inquiry;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository("inquiryRepository")
public class InquiryRepositoryImpl implements InquiryRepository {
    private final Map<Integer, Inquiry> inquiryMap = new HashMap<>();
    private int id = 5;

    public InquiryRepositoryImpl() {
        LocalDateTime now = LocalDateTime.now();
        inquiryMap.put(1, new Inquiry(1, "불만 접수", "상품 불량", "구매한 사과가 썩어있어요.", now, "tom", new ArrayList<>(List.of("/static/image/1_1.jpeg"))));
        inquiryMap.put(2, new Inquiry(2, "제안", "상품 추가 요청", "다양한 종류의 과일을 추가해 주세요.", now, "고객2", new ArrayList<>(List.of("/static/image/2_1.png", "/static/image/2_2.png"))));
        inquiryMap.put(3, new Inquiry(3, "환불/교환", "환불 요청", "구매한 바나나를 환불하고 싶습니다.", now, "고객3", new ArrayList<>(List.of("/static/image/3_1.png"))));
        inquiryMap.put(4, new Inquiry(4, "칭찬해요", "좋은 서비스", "직원들이 매우 친절했어요.", now, "고객4", new ArrayList<>(List.of("/static/image/4_1.jpeg"))));
        inquiryMap.put(5, new Inquiry(5, "기타 문의", "영업 시간", "주말에도 영업하시나요?", now, "고객5", new ArrayList<>(List.of("/static/image/5_1.png"))));
    }

    @Override
    public boolean exists(String id) {
        return inquiryMap.containsKey(Integer.parseInt(id));
    }

    @Override
    public List<Inquiry> getInquiries() {
        return inquiryMap.values().stream().toList();
    }

    @Override
    public Inquiry getInquiry(String inquiryId) {
        return inquiryMap.get(Integer.parseInt(inquiryId));
    }

    @Override
    public List<Inquiry> getInquiriesByUser(String userId) {
        return getInquiries().stream()
                .filter(inquiry -> inquiry.getCreatedBy().equals(userId))
                .collect(Collectors.toList()).reversed();
    }

    @Override
    public List<Inquiry> getInquiriesByCategory(String userId, String categoryName) {
        return getInquiries().stream()
                .filter(inquiry -> inquiry.getCategory().equals(categoryName))
                .filter(inquiry -> inquiry.getCreatedBy().equals(userId))
                .collect(Collectors.toList()).reversed();
    }

    @Override
    public List<Inquiry> getInquiriesByNotAnswer() {
        return getInquiries().stream()
                .filter(Inquiry -> !Inquiry.isAnswerCheck())
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkAnswer(String id) {
        return getInquiry(id).getAnswerContent() != null;
    }

    @Override
    public void addInquiry(String category, String title, String content, String createdBy, List<String> filePaths) {
        LocalDateTime now = LocalDateTime.now();
        Inquiry inquiry = new Inquiry(++id, category, title, content, now, createdBy, filePaths);
        inquiryMap.put(inquiry.getId(), inquiry);
    }

    @Override
    public void answerToInquiry(int id, String answerContent, String answerCreatedBy) {
        LocalDateTime now = LocalDateTime.now();
        Inquiry inquiry = inquiryMap.get(id);

        inquiry.setAnswerCheck(true);
        inquiry.setAnswerContent(answerContent);
        inquiry.setAnswerCreatedAt(now);
        inquiry.setAnswerCreatedBy(answerCreatedBy);
    }

    @Override
    public int getInquiriesSize() {
        return inquiryMap.size();
    }
}
