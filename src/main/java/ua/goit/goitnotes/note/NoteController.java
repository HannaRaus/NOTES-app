package ua.goit.goitnotes.note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.note.service.NoteService;
import ua.goit.goitnotes.validation.ValidationService;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping(path = "/note")
public class NoteController {

    private final ValidationService validationService;
    private final NoteService noteService;

    @Autowired
    public NoteController(ValidationService validationService, NoteService noteService) {
        this.validationService = validationService;
        this.noteService = noteService;
    }

    @GetMapping(path = "/list")
    public String showNotes(Model model) {
        log.info("NoteController.showNotes()");
        Set<NoteDTO> notes = noteService.findAll();
        model.addAttribute("notes", notes);
        return "notes";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam(name = "id") UUID uuid) {
        log.info("NoteController.delete().");
        noteService.delete(uuid);
        return "redirect:/note/list";
    }
}
