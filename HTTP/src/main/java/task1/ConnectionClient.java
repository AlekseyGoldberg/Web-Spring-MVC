package task1;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

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
            this.request = new Request(in);
        } catch (IOException e) {
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//http://localhost:9999/my/?name=aleksey&age=19&name=ji
    @Override
    public void run() {
        try {
            // read only request line for simplicity
            // must be in form GET /path HTTP/1.1
            final var requestLine = in.readLine();
            final var parts = requestLine.split(" ");

            if (parts.length != 3) {
                // just close socket
                return;
            }

            final var path = parts[1];
            if (!validPaths.contains(path)) {
                out.write(("HTTP/1.1 404 Not Found\r\n"
                        + "Content-Length: 0\r\n"
                        + "Connection: close\r\n"
                        + "\r\n").getBytes());
                out.flush();
                return;
            }

            final var filePath = Path.of(".", "public", path);
            final var mimeType = Files.probeContentType(filePath);

            final var length = Files.size(filePath);
            out.write(("HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + mimeType + "\r\n"
                    + "Content-Length: " + length + "\r\n"
                    + "Connection: close\r\n" + "\r\n")
                    .getBytes());
            Files.copy(filePath, out);
            out.flush();
            Map<String,String> params=request.getQueryParams();
            for (Map.Entry<String,String> i:params.entrySet()){
                System.out.println(i.getKey()+" "+i.getValue());
            }
        } catch (IOException | URISyntaxException exception) {
            exception.printStackTrace();
        }
    }
}
