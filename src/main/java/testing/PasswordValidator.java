package testing;

public class PasswordValidator {

    public boolean isPasswordValid(String password) {

        if (password == null) {
            return false;
        }

        if (!(password.matches(".*[0-9].*"))) {
            return false;
        }

        if (password.length() < 8 || password.length() > 21) {
            return false;
        }

        if (password.contains(" ")) {
            return false;
        }

        if (!password.matches(".*[a-z].*")) {
            return false;
        }

        if (!password.matches(".*[A-Z].*")) {
            return false;
        }

        if (!password.matches(".*\\p{Punct}.*")) {
            return false;
        }

        return true;
    }
}