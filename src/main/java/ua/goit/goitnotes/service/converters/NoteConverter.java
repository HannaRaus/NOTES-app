package ua.goit.goitnotes.service.converters;

import ua.goit.goitnotes.dto.NoteDTO;
import ua.goit.goitnotes.model.entity.Enums.AccessType;
import ua.goit.goitnotes.model.entity.NoteDAO;
import ua.goit.goitnotes.service.UserService;

public class NoteConverter {

    private final UserService userService;

    public NoteConverter(UserService userService) {
        this.userService = userService;
    }

    public NoteDTO fromNote(NoteDAO noteDAO) {
        return new NoteDTO(noteDAO.getId(), noteDAO.getTitle(), noteDAO.getContent(),
                noteDAO.getAccessType().getAccessType(), noteDAO.getUser().getName());
    }

    public NoteDAO toNote(NoteDTO entity) {
        return new NoteDAO(entity.getId(), entity.getTitle(), entity.getContent(),
                AccessType.getAccess(entity.getAccessType()).get(), userService.findByName(entity.getUserName()));
    }
}
