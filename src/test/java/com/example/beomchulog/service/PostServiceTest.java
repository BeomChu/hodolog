package com.example.beomchulog.service;

import com.example.beomchulog.domain.Post;
import com.example.beomchulog.request.PostEdit;
import com.example.beomchulog.repository.PostRepository;
import com.example.beomchulog.request.PostCreate;
import com.example.beomchulog.request.PostSearch;
import com.example.beomchulog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired
    PostRepository postRepository;

    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){

        PostCreate postcreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다.")
                .build();

        postService.write(postcreate);

        assertEquals(1L, postRepository.count());

        Post post = postRepository.findAll().get(0);

        //제목입니다. 내용입니다.
    }

    @Test
    @DisplayName("글 1개 죄회")
    void test2(){
        //Given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        //When
        Post post = postService.get(requestPost.getId());

        //Then
        assertNotNull(post);
        assertEquals(post.getTitle(), "foo");
        assertEquals(post.getContent(), "bar");
    }

    @Test
    @DisplayName("글 1페이지 조회")
    void test3(){
        List<Post> requestPosts = IntStream.range(0, 30) // for i = 0; i< 30; i++ 이랑 같음
                .mapToObj(i -> Post.builder()
                            .title("범츄로그 제목 - " + i)
                            .content("내용 - "+ i)
                            .build())
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

//        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC);
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .size(10)
                .build();

        List<PostResponse> posts = postService.getList(postSearch);

        assertEquals(10L, posts.size());
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4(){
        //given
        Post post = Post.builder()
                .title("범츄")
                .content("ㅎㅇ")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("zzz")
                .content("hihihi")
                .build();

        postService.edit(post.getId(), postEdit);
    }

    @Test
    @DisplayName("글 제목 수정")
    void test5(){
        //given
        Post post = Post.builder()
                .title("범츄")
                .content("ㅎㅇ")
                .build();
        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("zzz")
                .content("hihihi")
                .build();

        postService.edit(post.getId(), postEdit);
    }

    @Test
    @DisplayName("글 1개 죄회")
    void test7(){
        //Given
        Post post = Post.builder()
                .title("호돌맨")
                .content("반포자이")
                .build();

        postRepository.save(post);

        //expected

        assertThrows(IllegalArgumentException.class, () -> {
            postService.get(post.getId() + 1L);
        }, "예외처리가 잘못 되었어요");
    }


}