package ua.goit.goitnotes.note.service.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.goit.goitnotes.interfaces.Convertor;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.note.model.AccessType;
import ua.goit.goitnotes.note.model.NoteDAO;
import ua.goit.goitnotes.user.service.UserService;

@Component
public class NoteConverter implements Convertor<NoteDAO, NoteDTO> {

    private final UserService userService;

    @Autowired
    public NoteConverter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public NoteDAO fromDTO(NoteDTO noteDTO) {
        return new NoteDAO(noteDTO.getId(), noteDTO.getTitle(), noteDTO.getContent(),
                AccessType.byName(noteDTO.getAccessType()), userService.findByName(noteDTO.getUserName()));
    }

    @Override
    public NoteDTO toDTO(NoteDAO noteDAO) {
        return new NoteDTO(noteDAO.getId(), noteDAO.getTitle(), noteDAO.getContent(),
                noteDAO.getAccessType().name(), noteDAO.getUser().getName());
    }
}
