package com.nhnacademy.customerservice.domain;

import lombok.Value;

@Value
public class InquiryAddRequest {
    String category;
    String title;
    String content;
    String createdBy;
    String filePath;
}
