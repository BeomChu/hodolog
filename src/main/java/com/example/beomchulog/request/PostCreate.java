package com.example.beomchulog.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요")
    private String title;
    @NotBlank(message = "컨텐트를 입력해주세")
    private String content;

    @Builder
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
