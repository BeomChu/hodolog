package com.example.beomchulog.controller;

import com.example.beomchulog.domain.Post;
import com.example.beomchulog.repository.PostRepository;
import com.example.beomchulog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    public void beforeEach(){
        postRepository.deleteAll();
    }


    @Test
    @DisplayName("posts요청시 HelloWorld를 출력한다.")
    void test() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\" : \"제목입니다\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello world"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title은 필수다.")
    void test2() throws Exception {

        Post request = Post.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        ObjectMapper om = new ObjectMapper();
        String json = om.writeValueAsString(request);

        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void test3() throws Exception {
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content("{\"title\" : \"제목입니다.\", \"content\": \"내용입니다.\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertEquals(1, postRepository.count());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {
        //given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(post);

        //expected
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());


        //then
    }

    @Test
    @DisplayName("글 1개 조회")
    void test5() throws Exception {
        //given
        List<Post> requestPosts = IntStream.range(0, 30) // for i = 0; i< 30; i++ 이랑 같음
                .mapToObj(i -> Post.builder()
                        .title("범츄로그 제목 - " + i)
                        .content("내용 - "+ i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        //expected
        mockMvc.perform(get("/posts?page=1")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());


        //then
    }



}