package ua.goit.goitnotes.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.goitnotes.note.model.NoteDAO;
import ua.goit.goitnotes.user.model.User;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteDAO, UUID> {

    Optional<NoteDAO> findByTitle(String title);

    Set<Optional<NoteDAO>> findByUser_Name(String userName);
}
