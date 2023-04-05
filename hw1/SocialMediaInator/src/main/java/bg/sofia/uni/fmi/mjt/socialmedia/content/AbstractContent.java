package bg.sofia.uni.fmi.mjt.socialmedia.content;

import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractContent implements Content {

    private static int numOfInstances = 0;

    private String id;
    private String description;
    private String owner;

    private int numberOfLikes = 0;
    private int numberOfComments = 0;

    private LocalDateTime publishedOn;

    private List<String> tags = new ArrayList<>();
    private List<String> comments = new ArrayList<>();
    private List<String> mentions = new ArrayList<>();
    private List<String> likes = new ArrayList<>();

    public AbstractContent(String description, LocalDateTime publishedOn, String owner) {
        this.description = description;
        this.publishedOn = publishedOn;
        this.owner = owner;
        this.id = buildId();
    }

    private String buildId() {
        return getSuffix() + owner + "-" + numOfInstances++;
    }

    protected abstract String getSuffix();

    public LocalDateTime getPublishedOn() {
        return publishedOn;
    }

    @Override
    public int getNumberOfLikes() {
        return numberOfLikes;
    }

    @Override
    public int getNumberOfComments() {
        return numberOfComments;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public Collection<String> getTags() {
        return tags;
    }

    @Override
    public Collection<String> getMentions() {
        return mentions;
    }

    @Override
    public void likeContent(String username) throws UsernameAlreadyExistsException {
        boolean alreadyLikedContent = likes.stream()
                .anyMatch(user -> user.equals(username));

        if (alreadyLikedContent) {
            throw new UsernameAlreadyExistsException(username + " user already liked that content");
        }

        numberOfLikes++;
        likes.add(username);
    }

    @Override
    public String getOwner() {
        return owner;
    }

    @Override
    public ContentType getType() {
        if (this instanceof Post) {
            return ContentType.POST;
        } else {
            return ContentType.STORY;
        }
    }

    @Override
    public List<String> getLikes() {
        return likes;
    }

    @Override
    public void addComment(String comment) {
        comments.add(comment);
    }

    @Override
    public List<String> getComments() {
        return comments;
    }
}
