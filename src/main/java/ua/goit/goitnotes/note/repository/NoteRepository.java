package ua.goit.goitnotes.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.goitnotes.note.model.NoteDAO;

import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteDAO, UUID> {
    Optional<NoteDAO> findByTitle(String title);
    Optional<NoteDAO> findById(UUID id);
}
