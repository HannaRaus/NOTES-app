package ua.goit.goitnotes.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class ValidateUserRequest {
    @NonNull
    private String name;
    @NonNull
    private String password;
}
