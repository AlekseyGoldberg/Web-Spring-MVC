package task1;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static void start() {
        ExecutorService threadPool = Executors.newFixedThreadPool(64);

        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            while (true) {
                ConnectionClient client=new ConnectionClient(serverSocket);
                threadPool.submit(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
