package ua.goit.goitnotes.note.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ua.goit.goitnotes.note.model.Note;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NoteRepository extends JpaRepository<Note, UUID> {
    Optional<Note> findByTitle(String title);

    Optional<Note> findById(UUID id);

    List<Note> findByUser_Name(String userName);

    @Query("""
    select n from Note n where (n.user.id = ?1 and n.title like %?2%) 
    or (n.user.id = ?1 and n.content like %?2%) order by n.title""")
    List<Note> findNotesForUserByMask(UUID userId, String mask);

}
