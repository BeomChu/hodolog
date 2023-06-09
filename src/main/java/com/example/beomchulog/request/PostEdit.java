package com.example.beomchulog.request;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
public class PostEdit {

    @NotBlank(message = "타이틀을 입력하세요")
    private String title;

    @NotBlank(message = "콘텐츠를 입력해주세요")
    private String content;



}
