package com.nhnacademy.customerservice.controller;

import com.nhnacademy.customerservice.domain.Inquiry;
import com.nhnacademy.customerservice.domain.InquiryAddRequest;
import com.nhnacademy.customerservice.exception.ValidationFailedException;
import com.nhnacademy.customerservice.repository.InquiryRepository;
import com.nhnacademy.customerservice.validator.InquiryAddRequestValidator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@Controller
public class InquiryAddController {
    private static final String UPLOAD_DIR = "src/main/resources/static/image/";
    private static final String SAVE_DIR = "/static/image/";
    private final InquiryRepository inquiryRepository;
    private final InquiryAddRequestValidator validator;

    public InquiryAddController(InquiryRepository inquiryRepository, InquiryAddRequestValidator validator) {
        this.inquiryRepository = inquiryRepository;
        this.validator = validator;
    }

    @GetMapping("/cs/inquiry")
    public String AddInquiryGet(@RequestParam(name = "id", required = false) String inquiryId,
                                Model model) {
        if(inquiryId != null) {
            Inquiry inquiry = inquiryRepository.getInquiry(inquiryId);
            model.addAttribute("inquiry", inquiry);
            return "inquiryAnswerForm";
        } else {
            return "inquiryAddForm";
        }

    }

    @PostMapping("/cs/inquiry")
    public String AddInquiryPost(@Validated @ModelAttribute InquiryAddRequest req,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "files", required = false) List<MultipartFile> files,
                                 HttpSession session) throws IOException {
        String userId = (String) session.getAttribute("userId");

        if (bindingResult.hasErrors()) {
            throw new ValidationFailedException(bindingResult);
        }

        List<String> filePaths = new ArrayList<>();
        int fileNumber = 0;
        for (MultipartFile file : files) {
            if (!file.isEmpty() && isFileExtensionCheck(file.getOriginalFilename())) {
                String newFileName = String.format("%s_%s%s", inquiryRepository.getInquiriesSize() + 1, ++fileNumber, Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".")));
                file.transferTo(Paths.get(UPLOAD_DIR + newFileName));
                filePaths.add(SAVE_DIR + newFileName);
            }
        }

        inquiryRepository.addInquiry(req.getCategory(), req.getTitle(), req.getContent(), userId, filePaths);

        return "redirect:/cs";
    }

    @InitBinder("inquiryAddRequest")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    public boolean isFileExtensionCheck(String fileName){
        String[] imageExtensions = {"gif", ".jpg", ".jpeg", ".png"};
        for(String extension : imageExtensions){
            if(fileName.endsWith(extension)){
                return true;
            }
        }
        return false;
    }


}
