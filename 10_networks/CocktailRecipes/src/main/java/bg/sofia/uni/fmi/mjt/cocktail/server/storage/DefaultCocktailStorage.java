package bg.sofia.uni.fmi.mjt.cocktail.server.storage;

import bg.sofia.uni.fmi.mjt.cocktail.server.Cocktail;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions.CocktailNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultCocktailStorage implements CocktailStorage {

    private static DefaultCocktailStorage instance;

    private Set<Cocktail> cocktails = new HashSet<>();

    private DefaultCocktailStorage() {}

    public static DefaultCocktailStorage getInstance() {
        if (instance == null) {
            instance = new DefaultCocktailStorage();
        }
        return instance;
    }

    @Override
    public void createCocktail(Cocktail cocktail) throws CocktailAlreadyExistsException {

        if (cocktails.stream().anyMatch(x -> x.name().equals(cocktail.name()))) {
            throw new CocktailAlreadyExistsException("This cocktail already exists");
        }

        cocktails.add(cocktail);
    }

    @Override
    public Collection<Cocktail> getCocktails() {
        return List.copyOf(cocktails);
    }

    @Override
    public Collection<Cocktail> getCocktailsWithIngredient(String ingredientName) {

        return cocktails.stream()
                .filter(cocktail -> cocktail.ingredients().stream()
                        .anyMatch(ingredient -> ingredient.name().equals(ingredientName)))
                .collect(Collectors.toList());
    }

    @Override
    public Cocktail getCocktail(String name) throws CocktailNotFoundException {

        if (!cocktails.stream().anyMatch(x -> x.name().equals(name))) {
            throw new CocktailNotFoundException("There is no cocktail with such name");
        }

        return cocktails.stream()
                .filter(x -> x.name().equals(name))
                .toList()
                .get(0);
    }
}
