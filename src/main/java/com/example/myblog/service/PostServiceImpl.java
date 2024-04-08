package com.example.myblog.service;

import com.example.myblog.dao.PostDao;
import com.example.myblog.dto.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostDao postDao;

    @Override
    public List<Post> getPostList(Map map) throws Exception {
        return postDao.getPostList(map);
    }

    @Override
    public int getDataCount(Map map) throws Exception {
        return postDao.getDataCount(map);
    }

    @Override
    public Post getReadPost(int postId) throws Exception {
        return postDao.getReadPost(postId);
    }

    @Override
    public void updateHitCount(int postId) throws Exception {
        postDao.updateHitCount(postId);
    }

    @Override
    public void insertPost(Post post) throws Exception {
        postDao.insertPost(post);
    }

    @Override
    public void updatePost(Post post) throws Exception {
        postDao.updatePost(post);
    }

    @Override
    public void deletePost(int postId) throws Exception {
        postDao.deletePost(postId);
    }


}
