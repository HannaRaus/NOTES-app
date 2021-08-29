package ua.goit.goitnotes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/notes")
public class NotesController {

//private final Services NotesService;

    @GetMapping("/create")
    public String ShowNewNotesPage(Model model) {
        return "noteNew";
    }

}
