package ua.goit.goitnotes.service;

import ua.goit.goitnotes.dto.NoteDTO;
import ua.goit.goitnotes.exeptions.ObjectNotFoundException;
import ua.goit.goitnotes.model.entity.NoteDAO;
import ua.goit.goitnotes.model.repository.NoteRepository;
import ua.goit.goitnotes.service.convertors.NoteConverter;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
public class NoteService implements Service<NoteDTO> {

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
        return noteConverter.toDTO(noteRepository.findByTitle(name));
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
}
