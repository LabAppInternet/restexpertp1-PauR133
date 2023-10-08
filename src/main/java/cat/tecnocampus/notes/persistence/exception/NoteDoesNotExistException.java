package cat.tecnocampus.notes.persistence.exception;

public class NoteDoesNotExistException extends RuntimeException {
    public NoteDoesNotExistException(String title) {
        super("Note " + title + "doesn't exist");
    }
}
