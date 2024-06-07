package com.nhnacademy.customerservice.domain;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inquiry {
    private int id;
    private String category;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String createdBy;
    private List<String> filePaths;

    private String answerContent;
    private LocalDateTime answerCreatedAt;
    private String answerCreatedBy;
    private boolean answerCheck;

    public Inquiry(int id, String category, String title, String content, LocalDateTime createdAt, String createdBy, List<String> filePaths) {
        this.id = id;
        this.category = category;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.filePaths = filePaths;
    }
}
