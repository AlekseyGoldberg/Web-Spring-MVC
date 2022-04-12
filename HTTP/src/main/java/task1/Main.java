package task1;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {


    public static void main(String[] args) throws IOException {
        start();
    }

    private static void start() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(64);

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            while (true) {
                ConnectionClient client=new ConnectionClient(serverSocket);
                executorService.submit(client);
                }
            }
        }
    }



