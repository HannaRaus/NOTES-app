package ua.goit.goitnotes.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class ValidateNoteRequest {
    @NonNull
    private String title;
    @NonNull
    private String content;
    @NonNull
    private String accessType;

    private String idString;
}
