package ua.goit.goitnotes.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ValidateUserRequest {
    private String name;
    private String password;
}
