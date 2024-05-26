package com.nhnacademy.customerservice;

import com.nhnacademy.customerservice.domain.InquiryAddRequest;
import com.nhnacademy.customerservice.validator.InquiryAddRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

public class InquiryAddRequestValidatorTest {

    private InquiryAddRequestValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new InquiryAddRequestValidator();
    }

    @Test
    public void testValidInquiryAddRequest() {

        InquiryAddRequest request = new InquiryAddRequest("불만 접수", "Valid title", "Valid content", "tom", null);
        Errors errors = new BeanPropertyBindingResult(request, "inquiryAddRequest");
        validator.validate(request, errors);

        assertFalse(errors.hasErrors());
    }

    @Test
    public void testInvalidTitleLength() {
        // 유효하지 않은 제목 길이
        InquiryAddRequest request = new InquiryAddRequest("불만 접수", "", "Valid content", "tom", null);

        Errors errors = new BeanPropertyBindingResult(request, "inquiryAddRequest");
        validator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals("INVALID_TITLE_LENGTH", errors.getFieldError("title").getCode());
    }

    @Test
    public void testInvalidContentLength() {
        // 유효하지 않은 본문 길이
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("a".repeat(40001)); // 본문에 추가할 문자열
        String content = contentBuilder.toString();

        InquiryAddRequest request = new InquiryAddRequest("불만 접수", "Valid title", content, "tom", null);

        Errors errors = new BeanPropertyBindingResult(request, "inquiryAddRequest");
        validator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals("INVALID_CONTENT_LENGTH", errors.getFieldError("content").getCode());
    }

    @Test
    public void testInvalidTitleContentLength() {
        // 유효하지 않은 제목, 본문 길이
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append("a".repeat(40001)); // 본문에 추가할 문자열
        String content = contentBuilder.toString();

        InquiryAddRequest request = new InquiryAddRequest("불만 접수", "", content, "tom", null);

        Errors errors = new BeanPropertyBindingResult(request, "inquiryAddRequest");
        validator.validate(request, errors);

        assertTrue(errors.hasErrors());
        assertEquals(2, errors.getErrorCount());
    }
}
