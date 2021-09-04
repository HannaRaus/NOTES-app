package ua.goit.goitnotes.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.goit.goitnotes.dto.NoteDTO;
import ua.goit.goitnotes.service.NoteService;
import ua.goit.goitnotes.service.ValidationService;
import ua.goit.goitnotes.validation.ValidateNoteRequest;
import ua.goit.goitnotes.validation.ValidateResponse;

@Slf4j
@Controller
@RequestMapping(path = "/note")
public class NotesController {
    private final ValidationService validationService;
    private final NoteService service;

    @Autowired
    public NotesController(ValidationService validationService, NoteService service) {
        this.validationService = validationService;
        this.service = service;
    }

    @GetMapping("/create")
    public String showNewNotesPage(Model model) {
        return "newNote";
    }

    @PostMapping("/create")
    @ResponseBody
    public ValidateResponse createNoteOrShowException(@RequestBody ValidateNoteRequest noteRequest) {
        ValidateResponse response = validationService.validateNote(noteRequest);
        if (response.isSuccess()) {
            NoteDTO note = new NoteDTO();
            note.setTitle(noteRequest.getTitle());
            note.setContent(noteRequest.getContent());
            note.setAccessType(noteRequest.getAccessType());
            note.setUserName(SecurityContextHolder.getContext().getAuthentication().getName());
            service.create(note);
        }
        return response;
    }
}
