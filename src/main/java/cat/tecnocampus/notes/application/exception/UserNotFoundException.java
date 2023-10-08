package cat.tecnocampus.notes.application.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String email) {
        super("User with email " + email + "doesn't exist");
    }
}
