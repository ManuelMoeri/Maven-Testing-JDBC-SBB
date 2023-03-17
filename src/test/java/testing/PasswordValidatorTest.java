package testing;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordValidatorTest {

    private PasswordValidator uut = new PasswordValidator();

    @Test
    public void testPasswordNull() {
        Assertions.assertFalse(this.uut.isPasswordValid(null));
    }

    @Test
    public void testPasswordTooShort() {
        Assertions.assertFalse(this.uut.isPasswordValid("1234567"));
    }

    @Test
    public void testPasswordTooLong() {
        Assertions.assertFalse(this.uut.isPasswordValid("ABCDEFGHIJKLMNOPQRSTU"));
    }

    @Test
    public void testPasswordContainsNoSpace() {
        Assertions.assertFalse(this.uut.isPasswordValid("ABCDEFGHIJKLMNOPQR T"));
    }

    @Test
    public void testPasswordContainsNoNumeric() {
        Assertions.assertFalse(this.uut.isPasswordValid("ABCDEFGHIJKLMNOPQRST"));
    }

    @Test
    public void testPasswordContainsNoLowercaseChar() {
        Assertions.assertFalse(this.uut.isPasswordValid("ABCDEFGHIJ0123456789"));
    }

    @Test
    public void testPasswordContainsNoUppercaseChar() {
        Assertions.assertFalse(this.uut.isPasswordValid("abcdefghij0123456789"));
    }

    @Test
    public void testPasswordContainsNoSpecialChar() {
        Assertions.assertFalse(this.uut.isPasswordValid("abcdeFGHIJ0123456789"));
    }

    @Test
    public void testPasswordValid() {
        Assertions.assertTrue(this.uut.isPasswordValid("abcdeFGHIJ01234$*%?+"));
    }
}
