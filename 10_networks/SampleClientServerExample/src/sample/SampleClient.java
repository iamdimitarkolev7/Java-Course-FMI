package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SampleClient {
    private static final int PORT = 7777;
    private static final String HOST_NAME = "localhost";
    private static final String QUIT_MESSAGE = "quit";

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST_NAME, PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Server connection established.");

            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                if (QUIT_MESSAGE.equals(message)) {
                    break;
                }

                System.out.println("Sending message <" + message + "> to the server...");

                writer.println(message); // send the message to the server

                String reply = reader.readLine(); // read the response from the server
                System.out.println("The server replied <" + reply + ">");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
