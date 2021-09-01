package ua.goit.goitnotes.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ValidateNoteRequest {
    private String title;
    private String content;
    private String accessType;
}
