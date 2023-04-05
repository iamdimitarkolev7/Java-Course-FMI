package bg.sofia.uni.fmi.mjt.socialmedia.content;

import java.time.LocalDateTime;
import java.util.Collection;

public class Story extends AbstractContent {
    private final String STORY_ID_SUFFIX = "story-";

    public Story(String description, LocalDateTime publishedOn, String owner) {
        super(description, publishedOn, owner);
    }

    protected String getSuffix() {
        return STORY_ID_SUFFIX;
    }
}
