package com.example.beomchulog.repository;

import com.example.beomchulog.domain.Post;
import com.example.beomchulog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
