package com.nhnacademy.customerservice.domain;


import lombok.Value;

@Value
public class AnswerAddRequest {
    int id;
    String answerContent;
    String answerCreatedBy;
}
