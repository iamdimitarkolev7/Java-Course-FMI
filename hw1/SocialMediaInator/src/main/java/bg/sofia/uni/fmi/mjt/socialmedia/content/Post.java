package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Collection;

public class Post extends AbstractContent {
    private final String POST_ID_SUFFIX = "post-";

    public Post(String description, LocalDateTime publishedOn, String owner) {
        super(description, publishedOn, owner);
    }

    protected String getSuffix() {
        return POST_ID_SUFFIX;
    }
}
