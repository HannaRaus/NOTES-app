package ua.goit.goitnotes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.goit.goitnotes.model.entity.User;
import ua.goit.goitnotes.service.UserService;
import ua.goit.goitnotes.service.ValidationService;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidateUserRequest;

@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ValidationService validationService;

    @GetMapping("/registration")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    @ResponseBody
    public ValidateResponse registerAndValidate(ValidateUserRequest userRequest) {
        ValidateResponse response = validationService.validateUser(userRequest);
        register(userRequest, response);
        return response;
    }

    @ModelAttribute("userForm")
    public User defaultUser() {
        return new User();
    }

    private void register(ValidateUserRequest userRequest, ValidateResponse response) {
        if (response.isSuccess()) {
            User user = new User();
            user.setName(userRequest.getName());
            user.setPassword(userRequest.getPassword());
            userService.create(user);
        }
    }
}
