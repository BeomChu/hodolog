package com.example.beomchulog.controller;

import com.example.beomchulog.domain.Post;
import com.example.beomchulog.request.PostCreate;
import com.example.beomchulog.request.PostSearch;
import com.example.beomchulog.response.PostResponse;
import com.example.beomchulog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/helloworld")
    public String get(){
        return "Hello world";
    }

    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) throws Exception {
        postService.write(request);
//        log.info("postCreate={}", params);
//        log.info("title={}, content={}", title, content);
//        log.info("params={}", params);
//        params.get("title");

//        if (bindingResult.hasErrors()){
//            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//            Map<String, String> errorMap = new HashMap<>();
//
//            for (FieldError error : fieldErrors) {
//                errorMap.put(error.getField(), error.getDefaultMessage());
//            }
//            return errorMap;
//        }


        return Map.of();
    }

    @GetMapping("/posts/{postId}")
    public Post get(@PathVariable(name = "postId") Long id) {
        return postService.get(id);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }
}
