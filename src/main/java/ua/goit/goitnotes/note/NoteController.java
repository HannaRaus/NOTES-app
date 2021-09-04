package ua.goit.goitnotes.note;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
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
@RequiredArgsConstructor
@RequestMapping(path = "/note")
public class NoteController {

    private final ValidationService validationService;
    private final NoteService noteService;

    @GetMapping(path = "/list")
    public String showNotes(Model model) {
        log.info("NoteController.showNotes()");
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        Set<NoteDTO> notes = noteService.findByUserName(currentPrincipalName);
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
