package ua.goit.goitnotes.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.goit.goitnotes.dto.NoteDTO;
import ua.goit.goitnotes.model.entity.NoteDAO;
import ua.goit.goitnotes.model.repository.NoteRepository;
import ua.goit.goitnotes.service.converters.NoteConverter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class NoteService implements ServiceInterface<NoteDTO> {

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
            return noteConverter.fromNote(note.get());
        } else {
            throw new UsernameNotFoundException("Note with specified ID not found");
        }
    }

    @Override
    public List<NoteDTO> findAll() {
        return noteRepository.findAll().stream()
                .map(noteConverter::fromNote)
                .collect(Collectors.toList());
    }

    @Override
    public void create(NoteDTO entity) {
        NoteDAO note = noteConverter.toNote(entity);
        noteRepository.save(note);
    }

    @Override
    public void update(NoteDTO entity) {
        NoteDAO note = noteConverter.toNote(entity);
        noteRepository.save(note);
    }

    @Override
    public void delete(NoteDTO entity) {
        NoteDAO note = noteConverter.toNote(entity);
        noteRepository.delete(note);
    }
}
