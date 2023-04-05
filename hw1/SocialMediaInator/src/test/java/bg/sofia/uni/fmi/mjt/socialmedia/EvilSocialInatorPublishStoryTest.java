package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class EvilSocialInatorPublishStoryTest {

    private EvilSocialInator evilSocialInator;

    @BeforeEach
    public void setUp() {
        evilSocialInator = new EvilSocialInator();
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

        Assertions.assertEquals(storyId0, "story-kolev7-0");
        Assertions.assertEquals(storyId1, "story-kolev7-1");

        String username1 = "kolev9";
        LocalDateTime publishedOn1 = LocalDateTime.now();
        String description1 = "The trip to London was amazing!";

        evilSocialInator.register(username1);

        String storyId2 = evilSocialInator.publishStory(username1, publishedOn1, description1);
        Assertions.assertEquals(storyId2, "story-kolev9-2");
    }
}
