package ua.goit.goitnotes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.goitnotes.model.entity.NoteDAO;

import java.util.UUID;

public interface NoteRepository extends JpaRepository<NoteDAO, UUID> {
}
