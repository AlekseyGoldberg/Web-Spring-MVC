package task1;

import org.apache.http.NameValuePair;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.util.List;

public class ConnectionClient implements Runnable {
    BufferedReader in;
    BufferedOutputStream out;
    Request request;

    private final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html",
            "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");

    public ConnectionClient(ServerSocket serverSocket) {
        try {
            var socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedOutputStream(socket.getOutputStream());
            this.request = new Request(in, out, validPaths);
        } catch (IOException e) {
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        List<NameValuePair> params = null;
        try {
            params = request.getQueryParams();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        for (NameValuePair param : params)
            System.out.println(param.getName()+" " + param.getValue());

        System.out.println("-----------------");
        List<NameValuePair> listOfParam = request.getQueryParam("name");
        System.out.println("List of params name:");
        for (NameValuePair param : listOfParam)
            System.out.println("nameParam: " + param.getName() + " valueParam: " + param.getValue());
    }
}
