package bg.sofia.uni.fmi.mjt.socialmedia.exceptions;

public class NoUsersException extends Throwable {
    public NoUsersException(String msg) {
        super(msg);
    }

    public NoUsersException(String msg,  Throwable cause) {
        super(msg, cause);
    }
}
