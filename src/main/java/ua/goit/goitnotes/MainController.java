package ua.goit.goitnotes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.note.service.NoteService;

import java.util.Set;


@Controller
@RequestMapping(path = "/")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private final NoteService noteService;

    @GetMapping
    public String doGet() {
        log.info("NoteController.showNotes() I'm rendering the \"notes\" page");
        return "redirect:/note/list";
    }

    @GetMapping("login")
    public String login(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Your username or password is invalid");
        }

        if (logout != null) {
            model.addAttribute("message", "You have been logged out");
        }
        log.info("I'm rendering the \"login\" page");
        return "login";
    }

    @GetMapping(path = "error")
    public String error(Model model, String error) {
        model.addAttribute("error", error);
        return "error";
    }
}
