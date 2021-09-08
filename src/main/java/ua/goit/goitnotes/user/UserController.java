package ua.goit.goitnotes.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.user.service.UserService;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidateUserRequest;
import ua.goit.goitnotes.validation.ValidationService;

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
    public ValidateResponse registerAndValidate(@RequestBody ValidateUserRequest userRequest) {
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
