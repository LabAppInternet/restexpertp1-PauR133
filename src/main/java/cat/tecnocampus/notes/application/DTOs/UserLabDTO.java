package cat.tecnocampus.notes.application.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class UserLabDTO {

    private String username;

    private String name;

    private String secondName;

    private String email;

    private final Map<String, NoteLabDTO> ownedNotes;

    public UserLabDTO() {
        ownedNotes = new HashMap<String, NoteLabDTO>();
    }

    public UserLabDTO(String username, String name, String secondName, String email) {
        ownedNotes = new HashMap<>();

        this.username = username;
        this.name = name;
        this.secondName = secondName;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getSecondName() {
        return secondName;
    }

    public String getEmail() {
        return email;
    }

    public List<NoteLabDTO> getOwnedNotes() {
        return new ArrayList<NoteLabDTO>(ownedNotes.values());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOwnedNotes(List<NoteLabDTO> ownedNotes) {
        this.ownedNotes.clear();
        ownedNotes.forEach(note -> this.ownedNotes.put(note.getTitle(), note));
    }
}