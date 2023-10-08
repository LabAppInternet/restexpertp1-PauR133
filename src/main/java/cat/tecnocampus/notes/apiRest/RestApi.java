package cat.tecnocampus.notes.apiRest;

import cat.tecnocampus.notes.application.DTOs.NoteLabDTO;
import cat.tecnocampus.notes.application.DTOs.UserLabDTO;
import cat.tecnocampus.notes.application.NotesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestApi {
    private NotesService notesService;

    public RestApi(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/users")
    public List<UserLabDTO> getAllUsers() {
        return notesService.getAllUsers();
    }

    @GetMapping("/users/{email}")
    public UserLabDTO getUser(@PathVariable String email) {
        return notesService.getUserLab(email);
    }

    @GetMapping("/users/{email}/ownedNotes")
    public List<NoteLabDTO> getUserOwnedNotes(@PathVariable String email) {
        return notesService.getOwnedNotes(email);
    }

    @GetMapping("/users/{email}/allowedEditNotes")
    public List<NoteLabDTO> getUserAllowedEditNotes(@PathVariable String email) {
        return notesService.getAllowedEditNotes(email);
    }

    @PostMapping("/users/{email}/notes")
    public void createNewNote(@PathVariable String email, @RequestBody NoteLabDTO notelab) {
        notesService.createNewNote(email, notelab.getTitle(), notelab.getContent());
    }

    @PutMapping("/users/{email}/notes")
    public void editNote(@PathVariable String email, @RequestBody NoteLabDTO notelab) {
        notesService.editNote(email, notelab.getTitle(), notelab.getContent());
    }

    @PostMapping("/users")
    public void createNewUser(@RequestBody UserLabDTO userLab) {
        notesService.createNewUser(userLab);
    }

    @PutMapping("/users/{ownerEmail}/allowed/{allowedUserEmail}/{title}")
    public void allowEdit(@PathVariable String ownerEmail, @PathVariable String allowedUserEmail, @PathVariable String title) {
        notesService.allowEditNoteToUser(ownerEmail, allowedUserEmail, title);
    }

    @DeleteMapping("/users/{email}/notes/{title}")
    public void deleteNote(@PathVariable String email, @PathVariable String title) {
        notesService.deleteNote(email, title);
    }
}
