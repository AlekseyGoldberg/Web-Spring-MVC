package repository;

import model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostRepository {
    List<Post> listOfPost;

    public PostRepository(){
        listOfPost=new CopyOnWriteArrayList<>();
    }
    public List<Post> all() {
        return listOfPost;
    }

    public Optional<Post> getById(long id) {
        Post foundPost=null;
        for (Post post:listOfPost)
            if (post.getId()==id)
                foundPost=post;
        return Optional.of(foundPost);
    }

    public Post save(Post post) {
        if (post.getId()==0){
            post.setId(listOfPost.size());
            listOfPost.add(post);
            System.out.println(post);
            return post;
        }else {
            long foundId=post.getId();
            for (Post foundPost:listOfPost){
                if (foundPost.getId()==foundId){
                    foundPost.setContent(post.getContent());
                    return post;
                }
            }
        }
        return null;
    }

    public Post removeById(long id) {
        for (int i=0;i<listOfPost.size();i++){
            if (listOfPost.get(i).getId()==id){
                Post deletePost=listOfPost.get(i);
                listOfPost.remove(i);
                return deletePost;
            }
        }
        return null;
    }
}