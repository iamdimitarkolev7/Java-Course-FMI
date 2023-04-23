package bg.sofia.uni.fmi.mjt.cocktail.server.storage.exceptions;

public class CocktailAlreadyExistsException extends Exception {

    public CocktailAlreadyExistsException(String msg) {
        super(msg);
    }

    public CocktailAlreadyExistsException(String msg,  Throwable cause) {
        super(msg, cause);
    }

}
