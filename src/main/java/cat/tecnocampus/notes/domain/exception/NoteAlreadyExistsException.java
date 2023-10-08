package cat.tecnocampus.notes.domain.exception;

public class NoteAlreadyExistsException extends RuntimeException{
    public NoteAlreadyExistsException(String title, String email) {
        super("Note with title " + title + " is already owned by " + email);
    }
}
