package ua.goit.goitnotes.note;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
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

    @GetMapping(path = "/search")
    public String showSearchForm(@RequestParam(name = "contains") String contains, Model model) {
        log.info("NoteController.showSearchForm()");
        String currentPrincipalName = SecurityContextHolder.getContext().getAuthentication().getName();
        List<NoteDTO> notes = noteService.findByUserNameAndContentLike(userService.findByName(currentPrincipalName).getId(), contains);

        model.addAttribute("notes", notes);
        return "notes";
    }

    @GetMapping(path = "/delete")
    public String delete(@RequestParam(name = "id") UUID uuid) {
        log.info("NoteController.delete().");
        NoteDTO note = noteService.findById(uuid);
        if (note.getUserName().equals(SecurityContextHolder.getContext().getAuthentication().getName())) {
            noteService.delete(uuid);
        } else {
            return "redirect:/error";
        }
        return "redirect:/note/list";
    }

    @GetMapping("/create")
    public String showNewNotesPage() {
        return "newNote";
    }

    @PostMapping("/create")
    @ResponseBody
    public ValidateResponse createNoteOrShowException(@RequestBody ValidateNoteRequest noteRequest) {
        log.info("NoteController.createNoteOrShowException()");
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
        log.info("NoteController.updateNoteOrShowException()");
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
        log.info("NoteController.noteToClient()");
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        NoteDTO note = noteService.findById(id);
        if (!noteService.isNotePresentForTheUser(note, currentUserName)) {
            throw new ObjectNotFoundException(String.format("note %s does not exist", id));
        }
        return note;

    }

    @GetMapping("/share")
    public String showFormattedNotePage(@RequestParam(name = "id") UUID id) {
        return "browsingNote";
    }

    @GetMapping("/formatted")
    @ResponseBody
    public FormattedNote formattedNote(@RequestParam(name = "id") UUID id) {
        log.info("NoteController.formattedNote()");
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        FormattedNote formattedNote = new FormattedNote();
        try {
            NoteDTO note = noteService.findById(id);
            if (note.getAccessType().equals(AccessType.PUBLIC.toString()) ||
                    (note.getUserName().equals(currentUserName))) {
                formattedNote.setTitle(note.getTitle());
                formattedNote.setContent(markdownProcessor.getHtml(note.getContent()));
            }
        } catch (RuntimeException ex) {
            return formattedNote;
        }
        return formattedNote;
    }
}