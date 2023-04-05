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

        Assertions.assertEquals(storyId0, "story-kolev7-4");
        Assertions.assertEquals(storyId1, "story-kolev7-5");

        String username1 = "kolev9";
        LocalDateTime publishedOn1 = LocalDateTime.now();
        String description1 = "The trip to London was amazing!";

        evilSocialInator.register(username1);

        String storyId2 = evilSocialInator.publishStory(username1, publishedOn1, description1);
        Assertions.assertEquals(storyId2, "story-kolev9-6");
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
}
