package bg.sofia.uni.fmi.mjt.cocktail.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CocktailServer {

    private static final int SERVER_PORT = 7777;
    private static final int MAX_EXECUTORS = 5;

    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(MAX_EXECUTORS);

        Thread.currentThread().setName("Server Thread");

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {

            System.out.println("Server is listening on port: " + SERVER_PORT);

            Socket clientSocket;

            while (true) {

                clientSocket = serverSocket.accept();

                //System.out.println("Accepted client request from: " + clientSocket.getInetAddress());

                ClientRequestHandler clientRequestHandler = new ClientRequestHandler(clientSocket);

                executor.execute(clientRequestHandler);
            }

        } catch(IOException e) {
            throw new RuntimeException("Error connecting to server");
        }
    }
}
