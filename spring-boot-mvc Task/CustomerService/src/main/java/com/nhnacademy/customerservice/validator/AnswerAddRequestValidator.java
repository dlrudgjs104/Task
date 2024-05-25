package com.nhnacademy.customerservice.validator;

import com.nhnacademy.customerservice.domain.AnswerAddRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AnswerAddRequestValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AnswerAddRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AnswerAddRequest request = (AnswerAddRequest) target;
        String answerContent = request.getAnswerContent();

        if (!(!answerContent.isEmpty() && answerContent.length() <= 40000)) {
            errors.rejectValue("answerContent", "INVALID_ANSWER_CONTENT_LENGTH", "답변: 1~40,000자까지 가능");
        }
    }
}