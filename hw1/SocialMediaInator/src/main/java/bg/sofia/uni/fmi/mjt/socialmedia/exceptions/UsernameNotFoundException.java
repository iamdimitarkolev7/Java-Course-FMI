package bg.sofia.uni.fmi.mjt.socialmedia.exceptions;

public class UsernameNotFoundException extends Throwable {
    public UsernameNotFoundException(String msg) {
        super(msg);
    }

    public UsernameNotFoundException(String msg,  Throwable cause) {
        super(msg, cause);
    }
}
