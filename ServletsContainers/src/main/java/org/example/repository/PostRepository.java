package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class PostRepository {
    ConcurrentHashMap<Long, Post> listOfPost;
    AtomicLong newId;

    public PostRepository() {
        listOfPost = new ConcurrentHashMap<>();
        newId = new AtomicLong(1);
    }

    public List<Post> all() {
        return new ArrayList<>(listOfPost.values());
    }

    public Optional<Post> getById(long id) {
        Post foundPost = listOfPost.get(id);
        if (foundPost != null)
            return Optional.of(foundPost);
        else
            throw new NotFoundException("Not found id");
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            post.setId(newId.get());
            listOfPost.put(newId.getAndAdd(1), post);
            return post;
        } else {
            if (listOfPost.containsKey(post.getId()))
                listOfPost.get(post.getId()).setContent(post.getContent());
            else
                throw new NotFoundException("Not found id");
        }
        return post;
    }

    public Post removeById(long id) {
        Post deletePost = listOfPost.remove(id);
        if (deletePost != null) {
            return deletePost;
        } else {
            throw new NotFoundException("Not found id");
        }
    }
}