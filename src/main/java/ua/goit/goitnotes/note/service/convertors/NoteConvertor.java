package ua.goit.goitnotes.note.service.convertors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.goit.goitnotes.interfaces.Convertor;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.note.model.AccessType;
import ua.goit.goitnotes.note.model.Note;
import ua.goit.goitnotes.user.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NoteConvertor implements Convertor<Note, NoteDTO> {

    private final UserService userService;

    @Autowired
    public NoteConvertor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Note fromDTO(NoteDTO noteDTO) {
        log.info("fromDTO .");
        return new Note(noteDTO.getId(), noteDTO.getTitle(), noteDTO.getContent(),
                AccessType.byName(noteDTO.getAccessType()), userService.findByName(noteDTO.getUserName()));
    }

    @Override
    public NoteDTO toDTO(Note note) {
        log.info("toDTO .");
        return new NoteDTO(note.getId(), note.getTitle(), note.getContent(),
                note.getAccessType().name(), note.getUser().getName());
    }

    public List<NoteDTO> listToDTO(List<Note> notes){
        log.info("listToDTO .");
        return notes.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
