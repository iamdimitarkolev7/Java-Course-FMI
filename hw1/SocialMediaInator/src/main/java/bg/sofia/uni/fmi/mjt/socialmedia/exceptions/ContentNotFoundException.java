package bg.sofia.uni.fmi.mjt.socialmedia.exceptions;

public class ContentNotFoundException extends Throwable {
    public ContentNotFoundException(String msg) {
        super(msg);
    }

    public ContentNotFoundException(String msg,  Throwable cause) {
        super(msg, cause);
    }
}
