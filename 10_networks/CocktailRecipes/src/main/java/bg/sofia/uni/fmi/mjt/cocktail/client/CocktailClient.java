package bg.sofia.uni.fmi.mjt.cocktail.client;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.Ingredient;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CocktailClient {

    private static final int SERVER_PORT = 7777;

    private static final String HOST_NAME = "localhost";
    private static final String CREATE_PATTERN = "^create\\s+(\\w+)(?:\\s+(\\w+)=(\\d+)(ml|l))*$";
    private static final String GET_BY_NAME_PATTERN = "^get\\sby-name\\s(\\w+)$";
    private static final String GET_BY_INGREDIENT_PATTERN = "^get\\sby-ingredient\\s(\\w+)$";
    private static final String GET_ALL_COMMAND = "get all";
    private static final String DISCONNECT_COMMAND = "disconnect";

    private static void sendCreateDataToServer(String data, PrintWriter writer, BufferedReader reader) {

        List<String> tokens = List.of(data.split(" "));

        String cocktailName = tokens.get(1);
        Set<Ingredient> ingredients = new HashSet<>();

        if (tokens.size() > 2) {

            for (int i = 2; i < tokens.size(); i++) {
                List<String> ingredientTokens = List.of(tokens.get(i).split("="));
                String ingredient = ingredientTokens.get(0);
                String quantity = ingredientTokens.get(1);

                ingredients.add(new Ingredient(ingredient, quantity));
            }
        }

        Cocktail cocktail = new Cocktail(cocktailName, ingredients);

        Gson gson = new Gson();
        String cocktailJson = gson.toJson(cocktail);

        writer.println("create " + cocktailJson);

        try {
            String response = reader.readLine();
            System.out.println(response);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void requestGetAllDataFromServer(PrintWriter writer, BufferedReader reader) {

    }

    private static void requestGetByNameDataFromServer(String data, PrintWriter writer, BufferedReader reader) {
        return;
    }

    private static void requestGetByIngredientDataFromServer(String data, PrintWriter writer, BufferedReader reader) {
        return;
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
                    sendCreateDataToServer(input, writer, reader);
                }
                else if (input.matches(GET_ALL_COMMAND)) {
                    requestGetAllDataFromServer(writer, reader);
                }
                else if (input.matches(GET_BY_NAME_PATTERN)) {
                    requestGetByNameDataFromServer(input, writer, reader);
                }
                else if (input.matches(GET_BY_INGREDIENT_PATTERN)) {
                    requestGetByIngredientDataFromServer(input, writer, reader);
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
