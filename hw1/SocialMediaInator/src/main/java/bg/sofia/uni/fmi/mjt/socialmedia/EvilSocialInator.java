package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Post;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Story;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;
import bg.sofia.uni.fmi.mjt.socialmedia.user.UserActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

        if (userExists(username)) {
            throw new UsernameAlreadyExistsException(username + " username is already used!");
        }

        User newUser = new User(username);
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

        if (!userExists(username)) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        Post newPost = new Post(description, publishedOn, username);

        User currentUser = getUserByUsername(username);
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

        if (!userExists(username)) {
            throw new UsernameNotFoundException("No such user: " + username);
        }

        Story newStory = new Story(description, publishedOn, username);

        User currentUser = getUserByUsername(username);
        currentUser.uploadStory(newStory);
        currentUser.updateActivityLog(UserActions.PUBLISH_STORY);

        return newStory.getId();
    }

    @Override
    public void like(String username, String id) {

    }

    @Override
    public void comment(String username, String text, String id) {

    }

    @Override
    public Collection<Content> getNMostPopularContent(int n) {
        return null;
    }

    @Override
    public Collection<Content> getNMostRecentContent(String username, int n) {
        return null;
    }

    @Override
    public String getMostPopularUser() {
        return null;
    }

    @Override
    public Collection<Content> findContentByTag(String tag) {
        return null;
    }

    @Override
    public List<String> getActivityLog(String username) {
        return null;
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean userExists(String username) {
        return users.stream()
                .anyMatch(user -> user.getUsername().equals(username));
    }

    private User getUserByUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}
