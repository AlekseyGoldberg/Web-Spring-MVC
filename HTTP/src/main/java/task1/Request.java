package task1;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Request {
    private final String requestLine;
    private final BufferedOutputStream out;
    private List<NameValuePair> params;
    private final List<String> validPaths = List.of("/index.html", "/spring.svg", "/spring.png", "/resources.html",
            "/styles.css", "/app.js", "/links.html", "/forms.html", "/classic.html", "/events.html", "/events.js");

    public Request(BufferedReader in, BufferedOutputStream out) throws IOException, InterruptedException, URISyntaxException {
        this.requestLine = in.readLine();
        this.out = out;

//        раскомментировать при проверке дз "Формы и форматы данных"
//        Запрос: http://localhost:9998/my/?name=aleksey&age=19&name=ji
//        params = new ArrayList<>();
//        params = URLEncodedUtils.parse(new URI(requestLine.split(" ")[1]), "UTF-8");
    }

    public void getResponse() throws IOException {
            final var parts = requestLine.split(" ");
            if (parts.length != 3) {
                return;
            }
            final var path = parts[1];
            if (!validPaths.contains(path)) {
                send400Response();
                return;
            }
            send200Response(path);
        }

    public void send200Response(String path) throws IOException {
        final var filePath = Path.of(".", "HTTP/public", path);
//        Path filePath=Path.of("../Web,Spring & SpringMWC/HTTP/src/public"+path);
        final var template = Files.readString(filePath).getBytes();
        final var mimeType = Files.probeContentType(filePath);

        out.write((
                "HTTP/1.1 200 OK\r\n" +
                        "Content-Type: " + mimeType + "\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
        out.write(template);
        out.flush();
    }
    public void send200Response() throws IOException {
        out.write((
                "HTTP/1.1 200 OK\r\n" +
                        "Connection: close\r\n" +
                        "\r\n"
        ).getBytes());
        out.flush();
    }

    public void send400Response() throws IOException {
        out.write(("HTTP/1.1 404 Not Found\r\n"
                + "Content-Length: 0\r\n"
                + "Connection: close\r\n"
                + "\r\n").getBytes());
        out.flush();
    }

    public List<NameValuePair> getQueryParams() throws URISyntaxException {
        return params;
    }

    public List<NameValuePair> getQueryParam(String name) {
        List<NameValuePair> listOfParam = new ArrayList<>();
        for (NameValuePair param : params) {
            if (param.getName().equals(name))
                listOfParam.add(param);
        }
        return listOfParam;
    }

}
