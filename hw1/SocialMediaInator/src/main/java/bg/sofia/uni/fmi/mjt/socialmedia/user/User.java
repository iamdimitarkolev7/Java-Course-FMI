package bg.sofia.uni.fmi.mjt.socialmedia.user;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.content.ContentType;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Post;
import bg.sofia.uni.fmi.mjt.socialmedia.content.Story;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class User {
    private final String username;
    private List<Content> content = new ArrayList<>();
    private Queue<String> activityLogs = new PriorityQueue<>();

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void uploadPost(Post post) {
        content.add(post);
    }

    public void uploadStory(Story story) {
        content.add(story);
    }

    public void updateActivityLog(UserActions userAction) {

        switch (userAction) {
            case REGISTER -> addRegisterLog();
            case PUBLISH_POST -> addPublishPostLog();
            case PUBLISH_STORY -> addPublishStoryLog();
        }
    }

    public void updateActivityLog(UserActions userAction, String contentId, String contentOwner,
                                  ContentType contentType) {

        switch(userAction) {
            case LIKE_CONTENT -> addLikeContentLog(contentId, contentOwner, contentType);
            case COMMENT_CONTENT -> addCommentContentLog(contentId, contentOwner, contentType);
        }
    }

    private void addRegisterLog() {
        String logTxt = username + " registered at: " + LocalDateTime.now();

        activityLogs.add(logTxt);
    }

    private void addPublishPostLog() {
        List<Content> posts = getPosts();

        if (posts.isEmpty()) {
            return;
        }

        Post latestPost = (Post) posts.get(posts.size() - 1);
        String logTxt = username + " published a new post with id: "
                + latestPost.getId()
                + " at " + latestPost.getPublishedOn().toString();

        activityLogs.add(logTxt);
    }

    private void addPublishStoryLog() {
        List<Content> stories = getStories();

        if (stories.isEmpty()) {
            return;
        }

        Story latestStory = (Story) stories.get(stories.size() - 1);
        String logTxt = username + " published a new story with id: "
                + latestStory.getId()
                + " at " + latestStory.getPublishedOn().toString();

        activityLogs.add(logTxt);
    }

    private void addLikeContentLog(String id, String owner, ContentType contentType) {
        String logTxt = username + " liked a " + contentType.name().toLowerCase()
                + " with id " + id + " uploaded by " + owner;

        activityLogs.add(logTxt);
    }

    private void addCommentContentLog(String id, String owner, ContentType contentType) {
        String logTxt = username + " commented a " + contentType.name().toLowerCase()
                + " with id " + id + " uploaded by " + owner;

        activityLogs.add(logTxt);
    }

    public List<Content> getContent() {
        return content;
    }

    private List<Content> getPosts() {
        return content.stream()
                .filter(c -> c instanceof Post)
                .toList();
    }

    private List<Content> getStories() {
        return content.stream()
                .filter(c -> c instanceof Story)
                .toList();
    }
}
