package ua.goit.goitnotes.note;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.goit.goitnotes.exceptions.ObjectNotFoundException;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.note.service.NoteService;
import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.user.service.UserService;
import ua.goit.goitnotes.validation.ValidateNoteRequest;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidationService;

import java.util.Set;
import java.util.UUID;

@Slf4j
@Controller
@RequestMapping(path = "/note")
public class NoteController {

    private final ValidationService validationService;
    private final NoteService noteService;
    private final UserService userService;


    @Autowired
    public NoteController(ValidationService validationService, NoteService noteService, UserService userService) {
        this.validationService = validationService;
        this.noteService = noteService;
        this.userService = userService;
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

    @GetMapping("/create")
    public String showNewNotesPage() {
        return "newNote";
    }

    @PostMapping("/create")
    @ResponseBody
    public ValidateResponse createNoteOrShowException(@RequestBody ValidateNoteRequest noteRequest) {
        User currentUser = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        ValidateResponse response = validationService.validateNote(noteRequest, currentUser);
        if (response.isSuccess()) {
            NoteDTO note = new NoteDTO();
            note.setTitle(noteRequest.getTitle());
            note.setContent(noteRequest.getContent());
            note.setAccessType(noteRequest.getAccessType());
            note.setUserName(currentUser.getName());
            noteService.create(note);
        }
        return response;
    }

    @GetMapping("/edit")
    public String showUpdateNotesPage(@RequestParam(name = "id") UUID id) {
        return "updateNote";
    }

    @PostMapping("/edit")
    @ResponseBody
    public ValidateResponse updateNoteOrShowException(@RequestBody ValidateNoteRequest noteRequest) {
        User currentUser = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        ValidateResponse response = validationService.validateNote(noteRequest, currentUser);
        if (response.isSuccess()) {
            NoteDTO note = new NoteDTO();
            note.setId(UUID.fromString(noteRequest.getIdString()));
            note.setTitle(noteRequest.getTitle());
            note.setContent(noteRequest.getContent());
            note.setAccessType(noteRequest.getAccessType());
            note.setUserName(currentUser.getName());
            noteService.update(note);
        }
        return response;
    }
    @GetMapping
    @ResponseBody
    public NoteDTO noteToClient(@RequestParam(name = "id") UUID id){
        User currentUser = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!noteService.isNotePresentForTheUser(id, currentUser)) {
            throw new ObjectNotFoundException(String.format("note %s does not exist", id));
        }
        return noteService.findById(id);

    }
}
