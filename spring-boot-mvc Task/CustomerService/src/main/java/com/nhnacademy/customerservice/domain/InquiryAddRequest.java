package com.nhnacademy.customerservice.domain;


import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class InquiryAddRequest {
    String category;
    String title;
    String content;
    String createdBy;
    String filePath;
}
