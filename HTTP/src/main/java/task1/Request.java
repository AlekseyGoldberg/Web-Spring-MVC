package task1;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private String requestLine;
    private String startLine;
    private String headers;
    private String bodyRequest;
    private int indexEmptyLine;

    public Request(BufferedReader in) throws IOException, InterruptedException {
        this.requestLine = in.readLine();
//        startLine();
//        headers();
//        bodyRequest();
    }

    public Map<String, String> getQueryParams() throws URISyntaxException {
        Map<String, String> params = new HashMap<>();
        List<NameValuePair> listOfParams = URLEncodedUtils.parse(new URI(requestLine), "UTF-8");
        for (NameValuePair nvp : listOfParams)
            params.put(nvp.getName(), nvp.getValue());
        return params;
    }

    private void startLine() {
        String[] requestLineInChar = requestLine.split("");
        StringBuilder startLine = new StringBuilder();
        int index = 0;
        while (!requestLineInChar[index].equals("\n")) {
            startLine.append(requestLineInChar[index]);
            index++;
        }
        this.startLine = startLine.toString();
    }

    private void headers() {
        String[] requestLineInMass = requestLine.split("\n");
        StringBuilder headersBuilder = new StringBuilder();
        int index = 1;
        while (!requestLineInMass[index].isEmpty()) {
            headersBuilder.append(requestLineInMass[index]);
            index++;
        }
        this.headers = headersBuilder.toString();
        this.indexEmptyLine = index;
    }

    private void bodyRequest() {
        StringBuilder bodyBuilder = new StringBuilder();
        String[] requestLineInMass = requestLine.split("\n");

        for (int i = indexEmptyLine++; i < requestLineInMass.length; i++) {
            bodyBuilder.append(requestLineInMass[i]);
        }
        this.bodyRequest = bodyBuilder.toString();
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
