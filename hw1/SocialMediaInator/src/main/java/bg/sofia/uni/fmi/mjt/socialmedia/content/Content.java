package bg.sofia.uni.fmi.mjt.socialmedia.content;

import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;

import java.util.Collection;
import java.util.List;

public interface Content {
    /**
     * Returns the total number of likes.
     */
    int getNumberOfLikes();

    /**
     * Returns the total number of comments.
     */
    int getNumberOfComments();

    /**
     * Returns the unique id of the content
     */
    String getId();

    /**
     * Returns a Collection of all tags used in the description.
     * Аll tags should start with '#'.
     */
    Collection<String> getTags();

    /**
     * Returns a Collection of all users mentioned in the description.
     * Аll mentions should start with '@'.
     */
    Collection<String> getMentions();

    void likeContent(String username) throws UsernameAlreadyExistsException;

    String getOwner();

    ContentType getType();

    List<String> getLikes();
    void addComment(String comment);

    List<String> getComments();

    int getPopularity();
}