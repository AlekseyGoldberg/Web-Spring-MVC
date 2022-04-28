package repository;

import exception.NotFoundException;
import model.Post;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class PostRepository {
    List<Post> listOfPost;

    public PostRepository(){
        listOfPost=new LinkedList<>();
    }
    public List<Post> all() {
        return listOfPost;
    }

    public Optional<Post> getById(long id) {
        Post foundPost=null;
        for (Post post:listOfPost)
            if (post.getId()==id)
                foundPost=post;
        if (foundPost!=null)
            return Optional.of(foundPost);
        else
             throw new NotFoundException("Not found id");
    }

    public Post save(Post post) {
        if (post.getId()==0){
            post.setId(listOfPost.size()+1);
            listOfPost.add(post);
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
        throw new NotFoundException("Not found id");
    }

    public Post removeById(long id) {
        for (int i=0;i<listOfPost.size();i++){
            if (listOfPost.get(i).getId()==id){
                Post deletePost=listOfPost.get(i);
                listOfPost.remove(i);
                return deletePost;
            }
        }
        throw new NotFoundException("Not found id");
    }
}