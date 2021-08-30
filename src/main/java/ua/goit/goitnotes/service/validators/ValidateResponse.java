package ua.goit.goitnotes.service.validators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateResponse {

    private boolean success;

    private List<Errors> errors;

}
