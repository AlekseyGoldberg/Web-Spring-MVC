package task1;

import org.apache.http.NameValuePair;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.util.List;

public class ConnectionClient implements Runnable {
    BufferedReader in;
    BufferedOutputStream out;
    Request request;
    Socket socket;

    public ConnectionClient(ServerSocket serverSocket) {
        try {
            socket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        try {
            this.request = new Request(in, out);
//            Раскомментировать при проверке дз "HTTP и современный WEB" и закоментировать код ниже
//            Запрос: GET /spring.svg HTTP/1.1
            request.getResponse();

//            Раскомментировать при проверке дз "Формы и форматы данных" и закоментировать код выше
//            И в классе Request нужно раскомментировать нужный код в конструкторе
//            List<NameValuePair> params = null;
//            try {
//                params = request.getQueryParams();
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            }
//            System.out.println("All list of params: ");
//            for (NameValuePair param : params)
//                System.out.println(param.getName() + ": " + param.getValue());
//
//            List<NameValuePair> listOfParam = request.getQueryParam("name");
//            System.out.println("List of params name:");
//            for (NameValuePair param : listOfParam)
//                System.out.println("nameParam: " + param.getName() + " valueParam: " + param.getValue());
//            System.out.println("---------------------------------------");
//            request.send200Response();

            socket.close();
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
