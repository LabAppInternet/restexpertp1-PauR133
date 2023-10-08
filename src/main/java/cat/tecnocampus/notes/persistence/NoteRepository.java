package cat.tecnocampus.notes.persistence;

import cat.tecnocampus.notes.domain.NoteLab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<NoteLab, String> {
}
