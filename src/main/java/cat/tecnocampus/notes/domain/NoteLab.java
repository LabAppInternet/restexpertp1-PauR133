package cat.tecnocampus.notes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class NoteLab {

    @Id
    private String title;
    private String content;

    private LocalDateTime dateCreation;

    private LocalDateTime dateEdit;

    @ManyToOne
    @JoinColumn(name = "owner_email")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserLab owner;

    @ManyToMany(mappedBy = "allowedEditNotes")
    private List<UserLab> allowedUsers = new ArrayList<>();

    public NoteLab() {
    }

    public UserLab getOwner() {
        return owner;
    }

    public void setOwner(UserLab owner) {
        this.owner = owner;
    }

    public NoteLab(String title, String content) {
        this.title = title;
        this.content = content;
        dateCreation = LocalDateTime.now();
        dateEdit = LocalDateTime.now();
    }

    public void addAllowedUser(UserLab userLab) {
        allowedUsers.add(userLab);
    }
    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getDateEdit() {
        return dateEdit;
    }

    public void edit(String content) {
        this.content = content;
        this.dateEdit = LocalDateTime.now();
    }

    public String toString() {
        return "NoteLab: " + this.title + ", Content: " + this.content;
    }

}
