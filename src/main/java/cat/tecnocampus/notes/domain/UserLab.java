package cat.tecnocampus.notes.domain;

import cat.tecnocampus.notes.domain.exception.NotEditableNote;
import cat.tecnocampus.notes.domain.exception.NoteNotOwnedException;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Entity
public class UserLab {

    private String username;
    private String name;
    private String secondName;
    @Id
    private String email;

    @OneToMany(mappedBy = "owner",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private final Map<String, NoteLab> ownedNotes;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "note_allowed",
            joinColumns = @JoinColumn(name = "allowed_email"),
            inverseJoinColumns = @JoinColumn(name = "note_title")
    )
    private final Map<String, NoteLab> allowedEditNotes;

    public UserLab() {
        ownedNotes = new HashMap<String, NoteLab>();
        allowedEditNotes = new HashMap<>();
    }

    public UserLab(String username, String name, String secondName, String email) {
        ownedNotes = new HashMap<>();
        allowedEditNotes = new HashMap<>();

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

    public List<NoteLab> getOwnedNotes() {
        return new ArrayList<NoteLab>(ownedNotes.values());
    }

    public List<NoteLab> getAllowedEditNotes() {
        return new ArrayList<NoteLab>(allowedEditNotes.values());
    }

    public void setName(String name) {
        this.name = name;
    }

    public NoteLab getOwnedNote(String title) {
        NoteLab note = ownedNotes.get(title);
        if (note == null) {
            throw new NoteNotOwnedException(title, this.email);
        }
        return note;
   }

    public NoteLab getAllowedNote(String title) {
        NoteLab note = allowedEditNotes.get(title);
        if (note == null) {
            throw new NoteNotOwnedException(title, this.email);
        }
        return note;
    }

    public NoteLab getEitherAllowedOrOwnedNote(String title) {
        NoteLab note = ownedNotes.get(title);
        if (note == null) {
            note = allowedEditNotes.get(title);
        }
        if (note == null) {
            throw new NoteNotOwnedException(title, this.email);
        }
        return note;
    }

    public void writeNoteLab(String title, String contents) {
        if(ownedNotes.containsKey(title)) {
            throw new RuntimeException("Note's title is repeated");
        } else {
            NoteLab noteLab = new NoteLab(title, contents);
            noteLab.setOwner(this);
            ownedNotes.put(title, noteLab);

        }
    }

    public void removeNote(String title) {
        ownedNotes.remove(title);
   }

    public boolean doesOwnNote(String title) {
        return ownedNotes.containsKey(title);
    }

    private void addAsEditableNote(NoteLab allowedNote) {
        allowedNote.addAllowedUser(this);
        allowedEditNotes.put(allowedNote.getTitle(), allowedNote);
    }

    public void allowEdit(String title, UserLab allowedUser) {
        allowedUser.addAsEditableNote(this.getOwnedNote(title));
     }

    //edits either an owned or allowed note
    public void editNote(String title, String newContent) {
        if (ownedNotes.containsKey(title)) {
            ownedNotes.get(title).edit(newContent);
        } else if (allowedEditNotes.containsKey(title)) {
            allowedEditNotes.get(title).edit(newContent);
        } else {
            throw new NotEditableNote(title, this.email);
        }
    }

    public String toString() {
        String value = "Usuari: " + this.username + ", " + this.name + " " + this.secondName + " " + this.email;
        return value;
    }

    // Setters added for modelMapper be able to map from UserLab to UserLabDTO
    public void setUsername(String username) {
        this.username = username;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}