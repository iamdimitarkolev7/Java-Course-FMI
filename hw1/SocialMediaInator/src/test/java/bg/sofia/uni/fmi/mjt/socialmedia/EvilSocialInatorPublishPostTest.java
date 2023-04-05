package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class EvilSocialInatorPublishPostTest {
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

        Assertions.assertEquals(postId0, "post-kolev7-0");
        Assertions.assertEquals(postId1, "post-kolev7-1");

        String username1 = "kolev9";
        LocalDateTime publishedOn1 = LocalDateTime.now();
        String description1 = "The trip to London was amazing!";

        evilSocialInator.register(username1);

        String postId2 = evilSocialInator.publishPost(username1, publishedOn1, description1);
        Assertions.assertEquals(postId2, "post-kolev9-2");
    }
}
