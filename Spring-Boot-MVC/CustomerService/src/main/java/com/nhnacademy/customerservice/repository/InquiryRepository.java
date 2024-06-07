package com.nhnacademy.customerservice.repository;

import com.nhnacademy.customerservice.domain.Inquiry;

import java.util.List;

public interface InquiryRepository {
    boolean exists(String id);

    List<Inquiry> getInquiries();

    Inquiry getInquiry(String inquiry);

    List<Inquiry> getInquiriesByUser(String userId);

    List<Inquiry> getInquiriesByCategory(String userId, String categoryName);

    List<Inquiry> getInquiriesByNotAnswer();

    boolean checkAnswer(String id);

    void addInquiry(String category, String title, String content, String createdBy, List<String> filePaths);

    void answerToInquiry(int id, String answerContent, String answerCreatedBy);

    int getInquiriesSize();
}
