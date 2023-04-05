package bg.sofia.uni.fmi.mjt.socialmedia.exceptions;

public class UsernameAlreadyExistsException extends Throwable {
    public UsernameAlreadyExistsException(String msg) {
        super(msg);
    }

    public UsernameAlreadyExistsException(String msg,  Throwable cause) {
        super(msg, cause);
    }
}
