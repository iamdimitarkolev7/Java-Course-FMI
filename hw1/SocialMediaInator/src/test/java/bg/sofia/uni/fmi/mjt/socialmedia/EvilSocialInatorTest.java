package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.content.Content;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.ContentNotFoundException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class EvilSocialInatorTest {
    private EvilSocialInator evilSocialInator;

    @BeforeEach
    public void setUp() {
        evilSocialInator = new EvilSocialInator();
    }

    @Test
    @DisplayName("Publishing a post with null parameters throws an IllegalArgumentException")
    public void testPublishPostMethodWithNullParameters() {
        String username = "";
        LocalDateTime publishedOn = LocalDateTime.MIN;
        String description = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           evilSocialInator.publishPost(username, publishedOn, description);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.publishPost("ivan", publishedOn, description);
        });
    }

    @Test
    @DisplayName("Publishing a post with invalid username should throw an Exception")
    public void testPublishingPostWithInvalidUsername() throws UsernameAlreadyExistsException, UsernameNotFoundException {
        String username = "kolev7";
        LocalDateTime publishedOn = LocalDateTime.now();
        String description = "We had a great journey!";

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            String postId = evilSocialInator.publishPost(username, publishedOn, description);
        });
    }

    @Test
    @DisplayName("Publish a post")
    public void testPublishingPost() throws UsernameAlreadyExistsException, UsernameNotFoundException {
        String username = "kolev7";
        LocalDateTime publishedOn = LocalDateTime.now();
        String description = "We had a great journey!";

        evilSocialInator.register(username);
        String postId0 = evilSocialInator.publishPost(username, publishedOn, description);
        String postId1 = evilSocialInator.publishPost(username, publishedOn, description);

        Assertions.assertEquals(postId0, "post-kolev7-1");
        Assertions.assertEquals(postId1, "post-kolev7-2");

        String username1 = "kolev9";
        LocalDateTime publishedOn1 = LocalDateTime.now();
        String description1 = "The trip to London was amazing!";

        evilSocialInator.register(username1);

        String postId2 = evilSocialInator.publishPost(username1, publishedOn1, description1);
        Assertions.assertEquals(postId2, "post-kolev9-3");
    }

    @Test
    @DisplayName("Publishing a story with null parameters throws an IllegalArgumentException")
    public void testPublishStoryMethodWithNullParameters() {
        String username = "";
        LocalDateTime publishedOn = LocalDateTime.MIN;
        String description = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.publishStory(username, publishedOn, description);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.publishStory("ivan", publishedOn, description);
        });
    }

    @Test
    @DisplayName("Publishing a story with invalid username should throw an Exception")
    public void testPublishingStoryWithInvalidUsername() throws UsernameAlreadyExistsException, UsernameNotFoundException {
        String username = "kolev7";
        LocalDateTime publishedOn = LocalDateTime.now();
        String description = "We had a great journey!";

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            String storyId = evilSocialInator.publishStory(username, publishedOn, description);
        });
    }

    @Test
    @DisplayName("Publish a story")
    public void testPublishingStory() throws UsernameAlreadyExistsException, UsernameNotFoundException {
        String username = "kolev7";
        LocalDateTime publishedOn = LocalDateTime.now();
        String description = "We had a great journey!";

        evilSocialInator.register(username);
        String storyId0 = evilSocialInator.publishStory(username, publishedOn, description);
        String storyId1 = evilSocialInator.publishStory(username, publishedOn, description);

        Assertions.assertEquals(storyId0, "story-kolev7-5");
        Assertions.assertEquals(storyId1, "story-kolev7-6");

        String username1 = "kolev9";
        LocalDateTime publishedOn1 = LocalDateTime.now();
        String description1 = "The trip to London was amazing!";

        evilSocialInator.register(username1);

        String storyId2 = evilSocialInator.publishStory(username1, publishedOn1, description1);
        Assertions.assertEquals(storyId2, "story-kolev9-7");
    }

    @Test
    @DisplayName("Liking content should throw an IllegalArgumentException when parameters are empty")
    public void testLikeThrowingIllegalArgumentException() {
        String username = "";
        String id = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.like(username, id);
        });
    }

    @Test
    @DisplayName("Liking content should throw an exception when user is invalid")
    public void testLikeThrowingInvalidUserException() {
        String username = "zzz";
        String id = "333";

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            evilSocialInator.like(username, id);
        });
    }

    @Test
    @DisplayName("Liking content should throw an exception when id is invalid")
    public void testLikeThrowingInvalidContentException() throws UsernameAlreadyExistsException {
        String username = "zzz";
        String id = "333";

        evilSocialInator.register(username);

        Assertions.assertThrows(ContentNotFoundException.class, () -> {
            evilSocialInator.like(username, id);
        });
    }

    @Test
    @DisplayName("Like content test")
    public void testLikeContent() throws UsernameAlreadyExistsException, UsernameNotFoundException, ContentNotFoundException {
        String usernameCreator = "kolev7";
        String usernameLiker = "mitko";

        evilSocialInator.register(usernameCreator);
        evilSocialInator.register(usernameLiker);

        String id1 = evilSocialInator.publishPost(usernameCreator, LocalDateTime.now(), "Description");
        Content content = evilSocialInator.getContentById(id1);

        evilSocialInator.like(usernameLiker, id1);

        Assertions.assertEquals(1, content.getNumberOfLikes());
        Assertions.assertTrue(content.getLikes().contains(usernameLiker));

        String usernameLiker1 = "mitko1";

        evilSocialInator.register(usernameLiker1);
        evilSocialInator.like(usernameLiker1, id1);

        Assertions.assertEquals(2, content.getNumberOfLikes());
        Assertions.assertTrue(content.getLikes().contains(usernameLiker1)
                              && content.getLikes().contains(usernameLiker));
    }

    @Test
    @DisplayName("User likes content for the second time should throw an exception")
    public void testLikeContentTwiceException() throws UsernameAlreadyExistsException, UsernameNotFoundException, ContentNotFoundException {
        String usernameCreator = "kolev7";
        String usernameLiker = "mitko";

        evilSocialInator.register(usernameCreator);
        evilSocialInator.register(usernameLiker);

        String id1 = evilSocialInator.publishPost(usernameCreator, LocalDateTime.now(), "Description");
        Content content = evilSocialInator.getContentById(id1);

        evilSocialInator.like(usernameLiker, id1);

        Assertions.assertEquals(1, content.getNumberOfLikes());
        Assertions.assertTrue(content.getLikes().contains(usernameLiker));
        Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> {
            evilSocialInator.like(usernameLiker, id1);
        });
    }

    @Test
    @DisplayName("Comment method throws an IllegalArgumentException when given null parameters")
    public void testCommentInvalidArguments() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.comment("", "kkk", "kkk");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.comment("kkk", "", "kkk");
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.comment("kkk", "kkk", "");
        });
    }

    @Test
    @DisplayName("Comment method throws a UsernameNotFoundException when given non-registered username")
    public void testCommentInvalidUsername() {

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            evilSocialInator.comment("kolev111", "Hahaha that's funny", "story-kolev7-01");
        });
    }

    @Test
    @DisplayName("Comment method throws a ContentNotFoundException when given a wrong id")
    public void testCommentInvalidId() {

        Assertions.assertThrows(ContentNotFoundException.class, () -> {
           evilSocialInator.register("kolev7");

           evilSocialInator.comment("kolev7", "Hahaha", "monkey-19");
        });
    }

    @Test
    @DisplayName("Successfully add a comment")
    public void testAddingComments() throws UsernameAlreadyExistsException, UsernameNotFoundException, ContentNotFoundException {

        evilSocialInator.register("kolev7");
        evilSocialInator.register("kolev9");

        String id = evilSocialInator.publishPost("kolev7", LocalDateTime.now(), "A wonderful picture");

        Content content = evilSocialInator.getContentById(id);

        Assertions.assertEquals(0, content.getComments().size());

        evilSocialInator.comment("kolev9", "Nice one!", id);

        Assertions.assertEquals(1, content.getComments().size());
    }

    @Test
    @DisplayName("Get N most popular content with negative number parameter should throw exception")
    public void testGettingNMostPopularContentThrowingException() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           evilSocialInator.getNMostPopularContent(-154);
        });
    }

    @Test
    @DisplayName("Successfully get N most popular contents")
    public void testGettingNMostPopularContents() throws UsernameAlreadyExistsException, UsernameNotFoundException, ContentNotFoundException {

        evilSocialInator.register("kolev7");
        evilSocialInator.register("kolev9");
        evilSocialInator.register("kolev10");
        evilSocialInator.register("kolev11");

        String id1 = evilSocialInator.publishPost("kolev7", LocalDateTime.now(), "It's been a good day!");
        String id2 = evilSocialInator.publishStory("kolev7", LocalDateTime.now(), "Nice trip!");
        String id3 = evilSocialInator.publishPost("kolev7", LocalDateTime.now(), "Winner!");
        String id4 = evilSocialInator.publishStory("kolev9", LocalDateTime.now(), "good game!");

        evilSocialInator.comment("kolev9", "Congratulations!", id3);
        evilSocialInator.comment("kolev10", "Great achievement!", id3);
        evilSocialInator.comment("kolev11", "Amazing! I'm proud of you!", id3);
        evilSocialInator.like("kolev9", id3);
        evilSocialInator.like("kolev10", id3);
        evilSocialInator.like("kolev11", id3);

        evilSocialInator.comment("kolev9", "Wonderful place!", id2);
        evilSocialInator.comment("kolev10", "Great place!", id2);
        evilSocialInator.like("kolev9", id2);
        evilSocialInator.like("kolev11", id2);

        Collection<Content> contents = evilSocialInator.getNMostPopularContent(2);

        Assertions.assertEquals(2, contents.size());
        Assertions.assertEquals(3, contents.stream().toList().get(0).getNumberOfComments());
        Assertions.assertEquals(3, contents.stream().toList().get(0).getNumberOfLikes());
        Assertions.assertEquals(2, contents.stream().toList().get(1).getNumberOfComments());
        Assertions.assertEquals(2, contents.stream().toList().get(1).getNumberOfComments());
    }

    @Test
    @DisplayName("Get N most recent contents should throw an exception when given illegal arguments")
    public void testGettingNRecentContentsThrowingException() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.getNMostRecentContent("", 3);
        });

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.getNMostRecentContent("kolev", -18);
        });
    }

    @Test
    @DisplayName("Get activity logs method should throw exceptions when given illegal arguments")
    public void testGettingActivityLogsThrowingException() {

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
           evilSocialInator.getActivityLog("");
        });

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            evilSocialInator.getActivityLog("kolev");
        });
    }
}
