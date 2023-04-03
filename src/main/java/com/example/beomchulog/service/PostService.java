package com.example.beomchulog.service;

import com.example.beomchulog.domain.Post;
import com.example.beomchulog.request.PostEdit;
import com.example.beomchulog.domain.PostEditor;
import com.example.beomchulog.repository.PostRepository;
import com.example.beomchulog.request.PostCreate;
import com.example.beomchulog.request.PostSearch;
import com.example.beomchulog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void write(PostCreate postCreate) {

        Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        postRepository.save(post);
    }


    /**
     * /posts -> 글 전체조회 ( 검색+ 페이징)
     * /posts/{postId} 단건 조회
     */
    public Post get(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 안흔 글입니다."));

        return post;

    }


    public List<PostResponse> getList(PostSearch postSearch) {
//        Pageable pageable = PageRequest.of(page,5);
        return postRepository.getList(postSearch).stream()
                .map(PostResponse::new)
                .collect(Collectors.toList());

    }

    @Transactional
    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        if (postEdit.getTitle() != null) {
            editorBuilder.title(postEdit.getTitle());
        }

        if (postEdit.getContent() != null) {
            editorBuilder.content(postEdit.getContent());
        }



        post.edit(editorBuilder.build());
    }
}
