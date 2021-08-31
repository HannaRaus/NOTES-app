package ua.goit.goitnotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.goit.goitnotes.exception.UserAlreadyExistException;
import ua.goit.goitnotes.model.entity.User;
import ua.goit.goitnotes.service.UserService;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute("userForm") User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "registration";
        }

        try {
            userService.register(user);
        } catch (UserAlreadyExistException ex) {
            model.addAttribute("message", "Please use another email");
            return "registration";
        }

        return "redirect:/login";
    }

    @ModelAttribute("userForm")
    public User defaultUser() {
        return new User();
    }
}
