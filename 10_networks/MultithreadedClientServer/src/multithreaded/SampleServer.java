package multithreaded;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SampleServer {

    public static final int SERVER_PORT = 7777;
    public static final int MAX_EXECUTORS = 4;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTORS);

        Thread.currentThread().setName("Sample Server Thread"); // for debug purposes

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server started and is listening for client requests...");

            Socket clientSocket;

            while (true) {

                clientSocket = serverSocket.accept();

                System.out.println("Accepted client request from: " + clientSocket.getInetAddress());

                ClientRequestHandler clientRequestHandler = new ClientRequestHandler(clientSocket);

                executor.execute(clientRequestHandler);
            }

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
