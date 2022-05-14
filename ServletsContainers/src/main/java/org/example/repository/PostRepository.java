package org.example.repository;

import org.example.exception.NotFoundException;
import org.example.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PostRepository {
    ConcurrentHashMap<Long,Post> listOfPost;
    public PostRepository(){
        listOfPost= new ConcurrentHashMap<>();
    }
    public List<Post> all() {
        return listOfPost.values().stream().toList();
    }

    public Optional<Post> getById(long id) {
        Post foundPost=listOfPost.get(id);
        if (foundPost!=null)
            return Optional.of(foundPost);
        else
             throw new NotFoundException("Not found id");
    }

    public Post save(Post post) {
        if (post.getId()==0){
            post.setId(listOfPost.size()+1);
            listOfPost.put((long)listOfPost.size()+1,post);
            return post;
        }else {
            long foundId=post.getId();
            for (Long foundPost:listOfPost.keySet()){
                if (foundPost==foundId){
                    listOfPost.get(foundPost).setContent(post.getContent());
                    return post;
                }
            }
        }
        throw new NotFoundException("Not found id");
    }
    public Post removeById(long id) {
        Post deletePost=listOfPost.remove(id);
        if (deletePost!=null){
            return deletePost;
        }else {
            throw new NotFoundException("Not found id");
        }
    }
}