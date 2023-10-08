package cat.tecnocampus.notes.apiRest;

import cat.tecnocampus.notes.application.DTOs.NoteLabDTO;
import cat.tecnocampus.notes.application.DTOs.UserLabDTO;
import cat.tecnocampus.notes.application.NotesService;
import cat.tecnocampus.notes.domain.UserLab;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@Tag(name = "Tutorial", description = "Tutorial management APIs")
@RestController
@Validated
public class RestApi {
    private NotesService notesService;

    public RestApi(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("/users")
    public List<UserLabDTO> getAllUsers() {
        return notesService.getAllUsers();
    }

    @Operation(
            summary = "Retrieve a Tutorial by Id",
            description = "Get a Tutorial object by specifying its id. The response is Tutorial object with id, title, description and published status.",
            tags = { "tutorials", "get" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserLab.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/users/{email}")
    public UserLabDTO getUser(@PathVariable @Email String email) {
        return notesService.getUserLab(email);
    }

    @GetMapping("/users/{email}/ownedNotes")
    public List<NoteLabDTO> getUserOwnedNotes(@PathVariable @Email String email) {
        return notesService.getOwnedNotes(email);
    }

    @GetMapping("/users/{email}/allowedEditNotes")
    public List<NoteLabDTO> getUserAllowedEditNotes(@PathVariable @Email String email) {
        return notesService.getAllowedEditNotes(email);
    }

    @PostMapping("/users/{email}/notes")
    public void createNewNote(@PathVariable @Email String email, @RequestBody NoteLabDTO notelab) {
        notesService.createNewNote(email, notelab.getTitle(), notelab.getContent());
    }

    @PutMapping("/users/{email}/notes")
    public void editNote(@PathVariable @Email String email, @RequestBody NoteLabDTO notelab) {
        notesService.editNote(email, notelab.getTitle(), notelab.getContent());
    }

    @PostMapping("/users")
    public void createNewUser(@RequestBody UserLabDTO userLab) {
        notesService.createNewUser(userLab);
    }

    @PutMapping("/users/{ownerEmail}/allowed/{allowedUserEmail}/{title}")
    public void allowEdit(@PathVariable @Email String ownerEmail, @PathVariable String allowedUserEmail, @PathVariable String title) {
        notesService.allowEditNoteToUser(ownerEmail, allowedUserEmail, title);
    }

    @DeleteMapping("/users/{email}/notes/{title}")
    public void deleteNote(@PathVariable @Email String email, @PathVariable String title) {
        notesService.deleteNote(email, title);
    }

    @PostMapping("/validateBody")
    ResponseEntity<String> validateBody(@Valid @RequestBody UserLab userLab) {
        return ResponseEntity.ok("valid");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
