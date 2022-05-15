package task1;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private final String requestLine;
    private String startLine;
    private String headers;
    private String bodyRequest;
    private int indexEmptyLine;
    private final List<String> validPaths;
    private final BufferedOutputStream out;
    private List<NameValuePair> params;
    public Request(BufferedReader in, BufferedOutputStream out, List<String> validPaths) throws IOException, InterruptedException, URISyntaxException {
        this.requestLine = in.readLine();
        this.out = out;
        this.validPaths = validPaths;
//        раскомментировать при проверке дз "Формы и форматы данных"
//        Запрос: http://localhost:9999/my/?name=aleksey&age=19&name=ji
//        params=new ArrayList<>();
//        params = URLEncodedUtils.parse(new URI(requestLine), "UTF-8");

//        Раскомментировать при проверке дз "HTTP и современный WEB"
//        Запрос: GET /spring.svg HTTP/1.1
//        startLine();
//        headers();
//        bodyRequest();
    }
    public void getResponse() {
        try {

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
        } catch (IOException e) {
        }
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

    private void startLine() {
        String[] requestLineInChar = requestLine.split(" ");
        StringBuilder startLine = new StringBuilder();
        int index = 0;
        while (index != 3) {
            startLine.append(requestLineInChar[index]);
            startLine.append(" ");
            index++;
        }
        System.out.println("startLine: " + startLine);
        this.startLine = startLine.toString();
    }

    private void headers() {
        String[] requestLineInMass = requestLine.split("\n");
        StringBuilder headersBuilder = new StringBuilder();
        if (requestLineInMass.length == 1) {
            this.headers = "";
        } else {
            int index = 1;
            while (!requestLineInMass[index].isEmpty()) {
                headersBuilder.append(requestLineInMass[index]);
                index++;
            }
            this.headers = headersBuilder.toString();
            this.indexEmptyLine = index;
        }
        System.out.println("headers: " + headers);
    }

    private void bodyRequest() {
        StringBuilder bodyBuilder = new StringBuilder();
        String[] requestLineInMass = requestLine.split("\n");
        if (requestLineInMass.length == 1) {
            this.bodyRequest = "";
        } else {
            for (int i = indexEmptyLine++; i < requestLineInMass.length; i++) {
                bodyBuilder.append(requestLineInMass[i]);
            }
            this.bodyRequest = bodyBuilder.toString();
        }
        System.out.println("body: " + bodyRequest);
    }

    public String getStartLine() {
        return startLine;
    }

    public String getHeaders() {
        return headers;
    }

    public String getBodyRequest() {
        return bodyRequest;
    }
}
