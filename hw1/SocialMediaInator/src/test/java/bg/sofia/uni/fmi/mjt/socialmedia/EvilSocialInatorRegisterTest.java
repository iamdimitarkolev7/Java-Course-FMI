package bg.sofia.uni.fmi.mjt.socialmedia;

import bg.sofia.uni.fmi.mjt.socialmedia.exceptions.UsernameAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.socialmedia.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EvilSocialInatorRegisterTest {
    private EvilSocialInator evilSocialInator;

    @BeforeEach
    public void setUp() {
        evilSocialInator = new EvilSocialInator();
    }

    @Test
    @DisplayName("Registering a new valid user")
    void testRegisterNewUser() throws UsernameAlreadyExistsException {
        String username = "kolev7";
        evilSocialInator.register(username);
        User user = evilSocialInator.getUserByUsername(username);

        Assertions.assertEquals(1, evilSocialInator.getUsers().size());
        Assertions.assertTrue(user != null);
    }

    @Test
    @DisplayName("Throw an IllegalArgument exception when giving an empty parameter")
    void testRegisterThrowingIllegalArgumentException() {
        String username = "";

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            evilSocialInator.register(username);
        });
    }

    @Test
    @DisplayName("Throw a UserAlreadyExists exception when trying to add an existing user")
    void testRegisterThrowingUserAlreadyExistsException() throws UsernameAlreadyExistsException {
        String username = "kolev7";
        evilSocialInator.register(username);

        Assertions.assertThrows(UsernameAlreadyExistsException.class, () -> {
            evilSocialInator.register(username);
        });
    }
}