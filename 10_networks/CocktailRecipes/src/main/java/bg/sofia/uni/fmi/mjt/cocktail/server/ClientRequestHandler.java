package bg.sofia.uni.fmi.mjt.cocktail.server;

import bg.sofia.uni.fmi.mjt.cocktail.server.storage.DefaultCocktailStorage;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientRequestHandler implements Runnable {

    private final Socket socket;
    private final DefaultCocktailStorage cocktailStorage = DefaultCocktailStorage.getInstance();

    private final Gson gson = new Gson();

    public ClientRequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        Thread.currentThread().setName("Client Request Handler for " + socket.getRemoteSocketAddress());

        try (PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String inputLine;

            while ((inputLine = br.readLine()) != null) {

                List<String> tokens = List.of(inputLine.split(" "));
                String command = tokens.get(0);

                switch (command) {

                    case "create": {
                        String jsonData = tokens.get(1);
                        addCocktailToStorage(jsonData, pw);
                    }
                    break;
                    case "get": {
                        String by = tokens.get(1);

                        if (by.equals("all")) {
                            getAllCocktails(pw);
                        }
                        else if (by.equals("by-name")) {
                            String cocktailName = tokens.get(2);
                            getCocktailByName(cocktailName, pw);
                        }
                        else if (by.equals("by-ingredient")) {
                            String ingredient = tokens.get(2);
                            getCocktailsByIngredient(ingredient, pw);
                        }
                    }
                    break;
                    default:
                        break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addCocktailToStorage(String data, PrintWriter response) {

        Cocktail cocktail = gson.fromJson(data, Cocktail.class);

        try {
            cocktailStorage.createCocktail(cocktail);
        } catch (CocktailAlreadyExistsException e) {
            response.println(e.getMessage());
        }

        response.println("Cocktail added successfully!");
    }

    private void getAllCocktails(PrintWriter response) {

        List<Cocktail> allCocktails = cocktailStorage.getCocktails().stream().toList();

        response.println(gson.toJson(allCocktails));
    }

    private void getCocktailByName(String cocktailName, PrintWriter response) {

        try {
            Cocktail cocktail = cocktailStorage.getCocktail(cocktailName);
            response.println(gson.toJson(cocktail));
        } catch (CocktailNotFoundException e) {
            response.println(e.getMessage());
        }
    }

    private void getCocktailsByIngredient(String ingredient, PrintWriter response) {

        List<Cocktail> cocktails = cocktailStorage.getCocktailsWithIngredient(ingredient).stream().toList();

        response.println(gson.toJson(cocktails));
    }
}
