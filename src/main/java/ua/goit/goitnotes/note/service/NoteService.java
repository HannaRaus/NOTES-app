package ua.goit.goitnotes.note.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.goit.goitnotes.error_handling.ObjectNotFoundException;
import ua.goit.goitnotes.interfaces.CrudService;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.note.model.Note;
import ua.goit.goitnotes.note.repository.NoteRepository;
import ua.goit.goitnotes.note.service.convertors.NoteConvertor;
import ua.goit.goitnotes.user.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class NoteService implements CrudService<NoteDTO> {

    private final NoteRepository noteRepository;
    private final NoteConvertor noteConvertor;

    @Override
    public NoteDTO findById(UUID uuid) {
        log.info("findById .");
        return noteRepository.findById(uuid).map(noteConvertor::toDTO)
                .orElseThrow(() -> new ObjectNotFoundException("object 'note' with specified ID not found"));
    }

    @Override
    public NoteDTO findByName(String name) {
        log.info("findByName .");
        return noteRepository.findByTitle(name).map(noteConvertor::toDTO)
                .orElseThrow(() -> new ObjectNotFoundException("object 'note' with specified ID not found"));
    }

    @Override
    public List<NoteDTO> findAll() {
        log.info("findAll .");
        return noteConvertor.listToDTO(noteRepository.findAll());
    }

    @Override
    public NoteDTO create(NoteDTO entity) {
        log.info("create .");
        Note note = noteConvertor.fromDTO(entity);
        return noteConvertor.toDTO(noteRepository.save(note));
    }

    @Override
    public NoteDTO update(NoteDTO entity) {
        log.info("update .");
        Note note = noteConvertor.fromDTO(entity);
        return noteConvertor.toDTO(noteRepository.save(note));
    }

    @Override
    public void delete(UUID id) {
        log.info("delete .");
        try {
            noteRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            log.error("delete . There are no notes with such id in database");
            throw new ObjectNotFoundException("There are no notes with such id in database");
        }
    }

    public boolean isTitlePresetForTheUser(String title, User user) {
        log.info("isTitlePresetForTheUser .");
        Optional<Note> note = noteRepository.findByTitle(title);
        return note.filter(value -> user.equals(value.getUser())).isPresent();
    }

    public boolean isNotePresentForTheUser(NoteDTO note, String userName) {
        log.info("isNotePresentForTheUser .");
        return note.getUserName().equals(userName);
    }

    public List<NoteDTO> findByUserName(String userName) {
        log.info("findByUserName .");
        return noteConvertor.listToDTO(noteRepository.findByUser_Name(userName));
    }

    public List<NoteDTO> findByUserNameAndContentLike(UUID userId, String contains) {
        log.info("findByUserNameAndContentLike .");
        return noteConvertor.listToDTO(noteRepository.findNotesForUserByMask(userId, contains));
    }
}
