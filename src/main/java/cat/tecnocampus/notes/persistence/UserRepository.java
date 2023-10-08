package cat.tecnocampus.notes.persistence;

import cat.tecnocampus.notes.domain.UserLab;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserLab,String> {
}
