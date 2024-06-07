package com.nhnacademy.customerservice;

import com.nhnacademy.customerservice.domain.AnswerAddRequest;
import com.nhnacademy.customerservice.validator.AnswerAddRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

public class AnswerAddRequestValidatorTest {
    private AnswerAddRequestValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new AnswerAddRequestValidator();
    }

    @Test
    public void testSupports() {
        // 지원하는 클래스인지 확인
        assertTrue(validator.supports(AnswerAddRequest.class));
    }

    @Test
    public void testValidAnswerContent() {
        // 유효한 답변 내용
        AnswerAddRequest request = new AnswerAddRequest(1, "Valid answerContent", "1");

        Errors errors = new BeanPropertyBindingResult(request, "answerAddRequest");
        validator.validate(request, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void testInvalidAnswerContent() {
        // 유효하지 않은 답변 내용 (최소 길이)
        AnswerAddRequest request = new AnswerAddRequest(1, "", "1");

        Errors errors = new BeanPropertyBindingResult(request, "answerAddRequest");
        validator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals("INVALID_ANSWER_CONTENT_LENGTH", errors.getFieldError("answerContent").getCode());
    }
}
