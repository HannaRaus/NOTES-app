package ua.goit.goitnotes.service.convertors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.goit.goitnotes.dto.NoteDTO;
import ua.goit.goitnotes.enums.AccessType;
import ua.goit.goitnotes.model.entity.NoteDAO;
import ua.goit.goitnotes.service.UserService;

@Component
public class NoteConverter implements Convertor<NoteDAO, NoteDTO>{

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
