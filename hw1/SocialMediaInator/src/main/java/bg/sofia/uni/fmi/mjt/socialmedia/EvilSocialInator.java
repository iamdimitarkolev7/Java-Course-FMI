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
import java.util.List;

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
