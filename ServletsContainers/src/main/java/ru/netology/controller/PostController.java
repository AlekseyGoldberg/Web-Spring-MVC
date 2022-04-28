package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.model.Post;
import org.springframework.stereotype.Controller;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;
@Controller
public class PostController {
    public static final String APPLICATION_JSON = "application/json";
    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    public void all(HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var data = service.all();
        final var gson = new Gson();
        response.getWriter().print(gson.toJson(data));
    }

    public void getById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var post = service.getById(id);
        final var gson = new Gson();
        if (post != null)
            response.getWriter().print(gson.toJson(post));
        else
            response.getWriter().print("HTTP/1.1 404 Not found id");
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        final var post = gson.fromJson(body, Post.class);
        final var data = service.save(post);
        if (data != null)
            response.getWriter().print(gson.toJson(data));
        else
            response.getWriter().print("HTTP/1.1 404 Post Not found");
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        var deletePost = service.removeById(id);
        if (deletePost != null)
            response.getWriter().print("HTTP/1.1 200 ok");
        else
            response.getWriter().print("HTTP/1.1 404 Post Not found");
    }
}