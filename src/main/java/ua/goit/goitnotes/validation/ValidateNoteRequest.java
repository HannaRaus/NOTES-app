package ua.goit.goitnotes.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import ua.goit.goitnotes.enums.AccessType;

@AllArgsConstructor
@Data
public class ValidateNoteRequest {
    private String title;
    private String content;
    private AccessType accessType;
}
