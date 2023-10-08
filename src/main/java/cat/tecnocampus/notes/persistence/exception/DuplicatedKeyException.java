package cat.tecnocampus.notes.persistence.exception;

public class DuplicatedKeyException extends RuntimeException {
    public DuplicatedKeyException(String id) {
        super("The Id " + id + "duplicated");
    }
}
