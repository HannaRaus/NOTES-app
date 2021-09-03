package ua.goit.goitnotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.goit.goitnotes.model.entity.User;
import ua.goit.goitnotes.service.UserService;
import ua.goit.goitnotes.service.ValidationService;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidateUserRequest;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    private UserService userService;
    private final ValidationService validationService;

    @Autowired
    public UserController(UserService userService, ValidationService validationService) {
        this.userService = userService;
        this.validationService = validationService;
    }

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    @ResponseBody
    public ValidateResponse register(ValidateUserRequest userRequest) {

        ValidateResponse response = validationService.validateUser(userRequest);
        if (response.isSuccess()) {
            User user = new User();
            user.setName(userRequest.getName());
            user.setPassword(userRequest.getPassword());
            userService.register(user);
        }

        return response;
    }

    @ModelAttribute("userForm")
    public User defaultUser() {
        return new User();
    }
}
