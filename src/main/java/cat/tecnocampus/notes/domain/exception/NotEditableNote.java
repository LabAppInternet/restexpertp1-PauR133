package cat.tecnocampus.notes.domain.exception;

public class NotEditableNote extends RuntimeException {
    public NotEditableNote(String title, String email) {
        super("User " + email + " can't edit note " + title);
    }
}
