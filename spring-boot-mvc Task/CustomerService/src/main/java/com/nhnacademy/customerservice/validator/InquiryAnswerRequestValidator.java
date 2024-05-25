package com.nhnacademy.customerservice.validator;

import com.nhnacademy.customerservice.domain.InquiryAddRequest;
import com.nhnacademy.customerservice.domain.AnswerAddRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class InquiryAnswerRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AnswerAddRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        InquiryAddRequest request = (InquiryAddRequest) target;
        String content = request.getContent();
        if (!(!content.isEmpty() && content.length() <= 40000)) {
            errors.rejectValue("content", "", "답변: 1~40,000자까지 가능");
        }
    }
}