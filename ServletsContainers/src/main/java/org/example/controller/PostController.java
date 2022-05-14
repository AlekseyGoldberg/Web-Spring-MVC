package controller;

import com.google.gson.Gson;
import exception.NotFoundException;
import model.Post;
import service.PostService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

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
        try {
            final var post = service.getById(id);
            final var gson = new Gson();
            response.getWriter().print(gson.toJson(post));
        } catch (NotFoundException exception) {
            badRequest(response, exception);
        }
    }

    public void save(Reader body, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            final var gson = new Gson();
            final var post = gson.fromJson(body, Post.class);
            final var data = service.save(post);
            response.getWriter().print(gson.toJson(data));
        } catch (NotFoundException exception) {
            badRequest(response, exception);
        }
    }

    public void removeById(long id, HttpServletResponse response) throws IOException {
        response.setContentType(APPLICATION_JSON);
        try {
            var deletePost = service.removeById(id);
            response.getWriter().print("HTTP/1.1 200 ok");
        } catch (NotFoundException exception) {
            badRequest(response, exception);
        }
    }

    private void badRequest(HttpServletResponse response, NotFoundException exception) throws IOException {
        response.getWriter().print("HTTP/1.1 404 " + exception.getMessage());
    }
}