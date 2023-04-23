package bg.sofia.uni.fmi.mjt.cocktail.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CocktailClient {

    private static final int SERVER_PORT = 7777;

    private static final String HOST_NAME = "localhost";
    private static final String CREATE_PATTERN = "^create\\s+(\\w+)(?:\\s+(\\w+)=(\\d+)(ml|l))*$";
    private static final String GET_BY_NAME_PATTERN = "^get\\sby-name\\s(\\w+)$";
    private static final String GET_BY_INGREDIENT_PATTERN = "^get\\sby-ingredient\\s(\\w+)$";
    private static final String GET_ALL_COMMAND = "get all";
    private static final String DISCONNECT_COMMAND = "disconnect";

    private static void sendCreateDataToServer(String data, Socket socket) {

    }

    private static String requestGetAllDataFromServer(Socket socket) {
        return null;
    }

    private static String requestGetByNameDataFromServer(String data, Socket socket) {
        return null;
    }

    private static String requestGetByIngredientDataFromServer(String data, Socket socket) {
        return null;
    }

    public static void main(String[] args) {

        try (Socket socket = new Socket(HOST_NAME, SERVER_PORT);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             Scanner scanner = new Scanner(System.in)) {

            Thread.currentThread().setName("Client Thread");
            System.out.println("Server connection established!");

            while (true) {

                System.out.print("Enter your command: ");
                String input = scanner.nextLine();

                if (input.equals(DISCONNECT_COMMAND)) {
                    break;
                }
                else if (input.matches(CREATE_PATTERN)) {
                    sendCreateDataToServer(input, socket);
                }
                else if (input.matches(GET_ALL_COMMAND)) {
                    String data = requestGetAllDataFromServer(socket);
                }
                else if (input.matches(GET_BY_NAME_PATTERN)) {
                    String data = requestGetByNameDataFromServer(input, socket);
                }
                else if (input.matches(GET_BY_INGREDIENT_PATTERN)) {
                    String data = requestGetByIngredientDataFromServer(input, socket);
                }
                else {
                    System.out.println("Invalid command!");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("There is a problem connecting to the server.");
        }
    }
}
