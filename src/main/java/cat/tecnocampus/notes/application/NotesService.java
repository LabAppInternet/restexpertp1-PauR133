package cat.tecnocampus.notes.application;

import cat.tecnocampus.notes.application.DTOs.NoteLabDTO;
import cat.tecnocampus.notes.application.DTOs.UserLabDTO;
import cat.tecnocampus.notes.domain.UserLab;
import cat.tecnocampus.notes.domain.NoteLab;
import cat.tecnocampus.notes.application.exception.UserNotFoundException;
import cat.tecnocampus.notes.persistence.NoteRepository;
import cat.tecnocampus.notes.persistence.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {
    private NoteRepository noteRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;


    public NotesService(NoteRepository noteRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public void createNewUser(UserLabDTO userLab) {
        userRepository.save(modelMapper.map(userLab, UserLab.class));
    }

    @Transactional
    public void createNewNote(String ownerEmail, String title, String contents) {
        Optional<UserLab> userLab = userRepository.findById(ownerEmail);
        userLab.orElseThrow(() -> new UserNotFoundException(ownerEmail))
                .writeNoteLab(title, contents);
    }

    @Transactional
    public void editNote(String userEmail, String title, String newContents) {
        Optional<UserLab> userLab = userRepository.findById(userEmail);
        userLab.orElseThrow(() -> new UserNotFoundException(userEmail))
                .editNote(title, newContents);
    }

    @Transactional
    public void allowEditNoteToUser(String ownerEmail, String allowedEmail, String title) {
        UserLab owner = this.getUserLabInternal(ownerEmail);
        UserLab allowedUser = this.getUserLabInternal(allowedEmail);
        owner.allowEdit(title, allowedUser);
    }

    public UserLabDTO getUserLab(String email) {
        UserLab userLab= userRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email));
        UserLabDTO userlabDTO = modelMapper.map(userLab, UserLabDTO.class);

        return userlabDTO;
    }

    private UserLab getUserLabInternal(String email) {
        return userRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email));
    }
    @Transactional
    public void deleteNote(String email, String title) {
        userRepository.findById(email).orElseThrow(() -> new UserNotFoundException(email))
                .removeNote(title);
    }

    public List<UserLabDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userLab -> modelMapper.map(userLab, UserLabDTO.class)).toList();
    }

    public List<NoteLabDTO> getOwnedNotes(String email) {
        Optional<UserLab> userLab = userRepository.findById(email);
        return userLab.orElseThrow(() -> new UserNotFoundException(email)).getOwnedNotes().stream()
                .map(noteLab -> modelMapper.map(noteLab, NoteLabDTO.class)).toList();
    }

    public List<NoteLabDTO> getAllowedEditNotes(String email) {
        Optional<UserLab> userLab = userRepository.findById(email);
        return userLab.orElseThrow(() -> new UserNotFoundException(email)).getAllowedEditNotes().stream()
                .map(noteLab -> modelMapper.map(noteLab, NoteLabDTO.class)).toList();
    }
}
