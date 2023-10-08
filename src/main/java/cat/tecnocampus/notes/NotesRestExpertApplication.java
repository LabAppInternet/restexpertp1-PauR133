package cat.tecnocampus.notes;

import cat.tecnocampus.notes.application.DTOs.UserLabDTO;
import cat.tecnocampus.notes.application.NotesService;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotesRestExpertApplication implements CommandLineRunner {

    private final NotesService notesService;

    public NotesRestExpertApplication(NotesService notesService) {
        this.notesService = notesService;
    }

    public static void main(String[] args) {
        SpringApplication.run(NotesRestExpertApplication.class, args);
    }

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        notesService.createNewUser(new UserLabDTO("jroure", "Josep", "Roure", "josep@tecnocampus.cat"));
        notesService.createNewUser(new UserLabDTO("arueda", "Alfredo", "Rueda", "alfredo@tecnocampus.cat"));
        notesService.createNewNote("josep@tecnocampus.cat", "Good morning", "Good morning to all the students of Internet Applications Lab");
        notesService.createNewNote("josep@tecnocampus.cat", "We're having fun", "Lab internet is a lot of fun. We are learning so many things");
        notesService.createNewNote("josep@tecnocampus.cat", "To be deleted", "We created this note for you to see how can be delete");
        notesService.allowEditNoteToUser("josep@tecnocampus.cat", "alfredo@tecnocampus.cat", "Good morning");
        notesService.editNote("alfredo@tecnocampus.cat", "Good morning", "Hey, this is Alfredo editing the note");

    }
}
