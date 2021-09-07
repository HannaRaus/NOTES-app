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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public Set<NoteDTO> findAll() {
        log.info("findAll .");
        return noteRepository.findAll().stream()
                .map(noteConvertor::toDTO)
                .collect(Collectors.toSet());
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
        Optional<Note> note = noteRepository.findByTitle(title);
        if (note.isPresent()) {
            return user.equals(note.get().getUser());
        }
        return false;
    }

    public boolean isNotePresentForTheUser(UUID id, User user) {
        Optional<Note> note = noteRepository.findById(id);
        if (note.isPresent()) {
            return user.equals(note.get().getUser());
        }
        return false;
    }

    public Set<NoteDTO> findByUserName(String userName) {
        Set<NoteDTO> notes = new HashSet<>();

        noteRepository.findByUser_Name(userName)
                .forEach(note -> note.ifPresent(noteDAO -> notes.add(noteConvertor.toDTO(noteDAO))));

        return notes;
    }
}
