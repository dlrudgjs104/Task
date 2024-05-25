package com.nhnacademy.customerservice.validator;

import com.nhnacademy.customerservice.domain.InquiryAddRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class InquiryAddRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {return InquiryAddRequest.class.equals(clazz);}

    @Override
    public void validate(Object target, Errors errors) {
        InquiryAddRequest request = (InquiryAddRequest) target;
        String title = request.getTitle();
        String content = request.getContent();

        if (!(title.length() >= 2 && title.length() <= 200)) {
            errors.rejectValue("title", "", "제목: 2~200자까지 가능");
        }

        if(!(content.length() >= 0 && content.length() <= 40000)) {
            errors.rejectValue("content", "", "본문: 0~40,000자까지 가능");
        }
    }
}