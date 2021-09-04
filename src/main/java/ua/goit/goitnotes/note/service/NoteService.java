package ua.goit.goitnotes.note.service;

import org.springframework.stereotype.Service;
import ua.goit.goitnotes.interfaces.CrudService;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.exceptions.ObjectNotFoundException;
import ua.goit.goitnotes.note.model.NoteDAO;
import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.note.repository.NoteRepository;
import ua.goit.goitnotes.note.service.convertors.NoteConverter;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class NoteService implements CrudService<NoteDTO> {

    private final NoteRepository noteRepository;
    private final NoteConverter noteConverter;

    public NoteService(NoteRepository noteRepository, NoteConverter noteConverter) {
        this.noteRepository = noteRepository;
        this.noteConverter = noteConverter;
    }

    @Override
    public NoteDTO findById(UUID uuid) {
        Optional<NoteDAO> note = noteRepository.findById(uuid);
        if (note.isPresent()) {
            return noteConverter.toDTO(note.get());
        } else {
            throw new ObjectNotFoundException("object 'note' with specified ID not found");
        }
    }

    @Override
    public NoteDTO findByName(String name) {
        return noteConverter.toDTO(noteRepository.findByTitle(name).
                orElseThrow(() -> new ObjectNotFoundException("object 'note' with specified ID not found")));
    }

    @Override
    public Set<NoteDTO> findAll() {
        return noteRepository.findAll().stream()
                .map(noteConverter::toDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public NoteDTO create(NoteDTO entity) {
        NoteDAO note = noteConverter.fromDTO(entity);
        return noteConverter.toDTO(noteRepository.save(note));
    }

    @Override
    public NoteDTO update(NoteDTO entity) {
        NoteDAO note = noteConverter.fromDTO(entity);
        return noteConverter.toDTO(noteRepository.save(note));
    }

    @Override
    public void delete(UUID id) {
        noteRepository.deleteById(id);
    }

    public boolean isTitlePresetForTheUser(String title, User user) {
        Optional<NoteDAO> note = noteRepository.findByTitle(title);
        if(note.isPresent()){
           return user.equals(note.get().getUser());
        }
        return false;
    }
}
