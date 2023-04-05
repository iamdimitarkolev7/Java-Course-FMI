package bg.sofia.uni.fmi.mjt.socialmedia.user;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Post;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Story;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class User {
    private final String username;
    private List<Post> posts = new ArrayList<>();
    private List<Story> stories = new ArrayList<>();
    private Queue<String> activityLogs = new PriorityQueue<>();

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void uploadPost(Post post) {
        posts.add(post);
    }

    public void uploadStory(Story story) {
        stories.add(story);
    }

    public void updateActivityLog(UserActions userAction) {

        switch (userAction) {
            case REGISTER -> addRegisterLog();
            case PUBLISH_POST -> addPublishPostLog();
            case PUBLISH_STORY -> addPublishStoryLog();
            case LIKE_CONTENT -> addLikeContentLog();
            case COMMENT_CONTENT -> addCommentContentLog();
        }

    }

    private void addRegisterLog() {
        String logTxt = username + " registered at: " + LocalDateTime.now();

        activityLogs.add(logTxt);
    }

    private void addPublishPostLog() {
        Post latestPost = posts.get(posts.size() - 1);
        String logTxt = username + " published a new post with id: "
                + latestPost.getId()
                + " at " + latestPost.getPublishedOn().toString();

        activityLogs.add(logTxt);
    }

    private void addPublishStoryLog() {

    }

    private void addLikeContentLog() {

    }

    private void addCommentContentLog() {

    }
}
