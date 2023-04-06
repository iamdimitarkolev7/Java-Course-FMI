package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Post;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Story;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;
import bg.sofia.uni.fmi.mjt.socialmedia.user.UserActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class EvilSocialInator implements SocialMediaInator {
    public List<User> users;

    public EvilSocialInator() {
        this.users = new ArrayList<>();
    }

    @Override
    public void register(String username) throws UsernameAlreadyExistsException {
        if (username.isBlank() || username.isEmpty()) {
            throw new IllegalArgumentException("Username should not be empty!");
        }

        User newUser = getUserByUsername(username);

        if (newUser != null) {
            throw new UsernameAlreadyExistsException(username + " username is already used!");
        }

        newUser = new User(username);
        users.add(newUser);
        newUser.updateActivityLog(UserActions.REGISTER);
    }

    @Override
    public String publishPost(String username, LocalDateTime publishedOn, String description) throws UsernameNotFoundException {
        if (username == null || username.isBlank() || username.isEmpty()) {
            throw new IllegalArgumentException("Username should not be empty!");
        }

        if (publishedOn == null) {
            throw new IllegalArgumentException("Published on should not be empty!");
        }

        if (description == null || description.isBlank() || description.isEmpty()) {
            throw new IllegalArgumentException("Description should not be empty!");
        }

        User currentUser = getUserByUsername(username);

        if (currentUser == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        Post newPost = new Post(description, publishedOn, username);

        currentUser.uploadPost(newPost);
        currentUser.updateActivityLog(UserActions.PUBLISH_POST);

        return newPost.getId();
    }

    @Override
    public String publishStory(String username, LocalDateTime publishedOn, String description) throws UsernameNotFoundException {
        if (username == null || username.isBlank() || username.isEmpty()) {
            throw new IllegalArgumentException("Username should not be empty!");
        }

        if (publishedOn == null) {
            throw new IllegalArgumentException("Published on should not be empty!");
        }

        if (description == null || description.isBlank() || description.isEmpty()) {
            throw new IllegalArgumentException("Description should not be empty!");
        }

        User currentUser = getUserByUsername(username);

        if (currentUser == null) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        Story newStory = new Story(description, publishedOn, username);

        currentUser.uploadStory(newStory);
        currentUser.updateActivityLog(UserActions.PUBLISH_STORY);

        return newStory.getId();
    }

    @Override
    public void like(String username, String id) throws UsernameNotFoundException, UsernameAlreadyExistsException, ContentNotFoundException {
        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new IllegalArgumentException("Username should not be empty!");
        }

        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new IllegalArgumentException("ID should not be empty!");
        }

        User currentUser = getUserByUsername(username);

        if (currentUser == null) {
            throw new UsernameNotFoundException("No such user!");
        }

        Content content = getContentById(id);

        if (content == null) {
            throw new ContentNotFoundException("No such content!");
        }

        content.likeContent(username);
        currentUser.updateActivityLog(UserActions.LIKE_CONTENT, content.getId(), content.getOwner(), content.getType());
    }

    @Override
    public void comment(String username, String text, String id) throws UsernameNotFoundException, ContentNotFoundException {
        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new IllegalArgumentException("Username should not be empty!");
        }

        if (text == null || text.isEmpty() || text.isBlank()) {
            throw new IllegalArgumentException("Text should not be empty!");
        }

        if (id == null || id.isEmpty() || id.isBlank()) {
            throw new IllegalArgumentException("Id should not be null!");
        }

        User currentUser = getUserByUsername(username);

        if (currentUser == null) {
            throw new UsernameNotFoundException("No such user!");
        }

        Content content = getContentById(id);

        if (content == null) {
            throw new ContentNotFoundException("Id is not valid!");
        }

        content.addComment(text);
        currentUser.updateActivityLog(UserActions.COMMENT_CONTENT, content.getId(), content.getOwner(), content.getType());
    }

    @Override
    public Collection<Content> getNMostPopularContent(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N should be a non-negative number!");
        }

        return users.stream()
                .flatMap(user -> user.getContent().stream())
                .sorted((c1, c2) -> c2.getPopularity() - c1.getPopularity())
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Content> getNMostRecentContent(String username, int n) {
        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new IllegalArgumentException("Username should not be empty!");
        }

        if (n < 0) {
            throw new IllegalArgumentException("N should be a non-negative integer");
        }

        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .flatMap(user -> user.getContent().stream())
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public String getMostPopularUser() {
        return users.stream()
                .max(Comparator.comparingInt(user -> user.getContent().stream()
                        .mapToInt(Content::getPopularity)
                        .sum()))
                .map(User::getUsername)
                .orElse("");
    }

    @Override
    public Collection<Content> findContentByTag(String tag) {
        return users.stream()
                .flatMap(user -> user.getContent().stream())
                .filter(c -> c.getTags().contains(tag))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getActivityLog(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty() || username.isBlank()) {
            throw new IllegalArgumentException("Username should not be empty!");
        }

        User user = getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("No such user!");
        }

        return user.getActivityLogs();
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public Content getContentById(String id) {
        return users.stream()
                .flatMap(user -> user.getContent().stream())
                .filter(content -> content.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
