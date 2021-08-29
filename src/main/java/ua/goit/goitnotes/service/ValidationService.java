package ua.goit.goitnotes.service;

import org.springframework.stereotype.Service;
import ua.goit.goitnotes.enums.AccessType;
import ua.goit.goitnotes.validation.ValidateNoteRequest;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidateUserRequest;
import ua.goit.goitnotes.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ValidationService {

    public ValidateResponse validateNote(ValidateNoteRequest noteRequest) {
        List<ValidationError> errors = new ArrayList<>();
        boolean success = true;
        String title = noteRequest.getTitle();
        String content = noteRequest.getContent();
        AccessType accessType = noteRequest.getAccessType();
        if (title.length() < 5 || title.length() > 100) {
            errors.add(ValidationError.WRONG_NOTE_TITLE_LENGTH);
        }
        if (content.length() < 5 || content.length() > 10000) {
            errors.add(ValidationError.WRONG_NOTE_CONTENT_LENGTH);
        }
        if (accessType == null) {
            errors.add(ValidationError.NOTE_ACCESS_TYPE_IS_NOT_CHOSEN);
        }
        if(errors.size() > 0){
            success = false;
        }
        return new ValidateResponse(success, errors);
    }

    public ValidateResponse validateUser(ValidateUserRequest userRequest) {
        boolean success = true;
        List<ValidationError> errors = new ArrayList<>();
        String name = userRequest.getName();
        String password = userRequest.getPassword();
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher matcher = pattern.matcher(name);
            if (name.length() < 5 || name.length() > 50) {
                errors.add(ValidationError.WRONG_USER_NAME_LENGTH);
            }
            if (!matcher.matches()) {
                errors.add(ValidationError.WRONG_USER_NAME_SYMBOLS);
            }
            if (password.length() < 8 || password.length() > 100) {
                errors.add(ValidationError.WRONG_USER_PASSWORD_LENGTH);
            }
            if(errors.size() > 0){
                success = false;
            }
        return new ValidateResponse(success, errors);
    }
}
