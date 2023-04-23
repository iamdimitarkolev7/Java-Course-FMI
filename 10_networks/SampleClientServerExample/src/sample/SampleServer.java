package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SampleServer {

    private static final int PORT = 7777;

    public static void main(String[] args) throws IOException {

        try (ServerSocket socket = new ServerSocket(PORT)) {
            System.out.println("Server started listening on port " + PORT);

            try (Socket clientSocket = socket.accept();
                 BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String inputLine;

                while ((inputLine = br.readLine()) != null) {
                    System.out.println("Message received from client: " + inputLine);
                    pw.println("Echo " + inputLine);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("There is a problem with the server socket", e);
        }
    }
}
