package cat.tecnocampus.notes.domain.exception;

public class NoteNotOwnedException extends RuntimeException{
    public NoteNotOwnedException(String title, String email) {
        super("Note with title " + title + " is not owned by " + email);
    }
}
