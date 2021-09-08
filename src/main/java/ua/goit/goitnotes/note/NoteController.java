package ua.goit.goitnotes.note;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.goit.goitnotes.error_handling.ObjectNotFoundException;
import ua.goit.goitnotes.note.dto.FormattedNote;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.note.model.AccessType;
import ua.goit.goitnotes.note.service.NoteService;
import ua.goit.goitnotes.note.service.processors.MarkdownProcessorCommonMarkdownImplementation;
import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.user.service.UserService;
import ua.goit.goitnotes.validation.ValidateNoteRequest;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidationService;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping(path = "/note")
public class NoteController {

    private final ValidationService validationService;
    private final NoteService noteService;
    private final UserService userService;
    private final MarkdownProcessorCommonMarkdownImplementation markdownProcessor;

    @GetMapping(path = "/list")
    public String showNotes(Model model) {
        log.info("NoteController.showNotes()");
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<NoteDTO> notes = noteService.findByUserName(currentPrincipalName).stream()
                .sorted(Comparator.comparing(NoteDTO::getTitle))
                .collect(Collectors.toList());
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
    public NoteDTO noteToClient(@RequestParam(name = "id") UUID id) {
        User currentUser = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!noteService.isNotePresentForTheUser(id, currentUser)) {
            throw new ObjectNotFoundException(String.format("note %s does not exist", id));
        }
        return noteService.findById(id);

    }

    @GetMapping("/share")
    public String showFormattedNotePage(@RequestParam(name = "id") UUID id) {
        return "browsingNote";
    }

    @GetMapping("/formatted")
    @ResponseBody
    public FormattedNote formattedNote(@RequestParam(name = "id") UUID id) {
        User currentUser = userService.findByName(SecurityContextHolder.getContext().getAuthentication().getName());
        FormattedNote formattedNote = new FormattedNote();
        try {
            NoteDTO note = noteService.findById(id);
            if (note.getAccessType().equals(AccessType.PUBLIC.toString()) ||
                    (Objects.nonNull(currentUser.getName()) &&
                            noteService.isNotePresentForTheUser(id, currentUser))) {
                formattedNote.setTitle(note.getTitle());
                formattedNote.setContent(markdownProcessor.getHtml(note.getContent()));
                return formattedNote;
            }
        } catch (RuntimeException ex) {
            return formattedNote;

        }
        return formattedNote;
    }
}