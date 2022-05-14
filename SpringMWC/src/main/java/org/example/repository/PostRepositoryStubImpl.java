package org.example.repository;

import org.example.exeption.NotFoundException;
import org.example.model.Post;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class PostRepositoryStubImpl implements PostRepository {
    ConcurrentHashMap<Long, Post> listOfPost;
    long newId;

    public PostRepositoryStubImpl() {
        listOfPost = new ConcurrentHashMap<>();
        newId = 0;
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
            post.setId(newId);
            listOfPost.put(newId, post);
            newId++;
        } else {
            try {
                listOfPost.get(post.getId()).setContent(post.getContent());
            } catch (NullPointerException e) {
                throw new NotFoundException("Not found id");
            }
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