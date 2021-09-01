package ua.goit.goitnotes.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.goit.goitnotes.validation.ValidateNoteRequest;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidateUserRequest;
import ua.goit.goitnotes.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class ValidationService {

    public ValidateResponse validateNote(@NonNull ValidateNoteRequest noteRequest) {
        log.info("validateNote .");
        List<ValidationError> errors = new ArrayList<>();
        boolean success = true;
        String title = noteRequest.getTitle();
        String content = noteRequest.getContent();
//        AccessType accessType = noteRequest.getAccessType();
        if (title.length() < 5 || title.length() > 100) {
            errors.add(ValidationError.WRONG_NOTE_TITLE_LENGTH);
            log.error("validateNote . note title:'{}' length should be between 5 and 100 included", title);
        }
        if (content.length() < 5 || content.length() > 10000) {
            errors.add(ValidationError.WRONG_NOTE_CONTENT_LENGTH);
            log.error("validateNote . note content length:'{}', but should be between 5 and 10000 included", content.length());
        }
//        if (accessType == null) {
//            errors.add(ValidationError.NOTE_ACCESS_TYPE_IS_NOT_CHOSEN);
//            log.error("validateNote . validation type is not chosen:'{}'", accessType);
//        }
//        if(accessType == AccessType.UNKNOWN){
//            errors.add(ValidationError.WRONG_ACCESS_TYPE);
//            log.error("validateNote . validation type is wrong:'{}'", accessType);
//        }
        if(errors.size() > 0){
            success = false;
            log.error("validateNote . note data is incorrect:'{}'", noteRequest);
        }
        return new ValidateResponse(success, errors);
    }

    public ValidateResponse validateUser(@NonNull ValidateUserRequest userRequest) {
        log.info("validateUser .");
        boolean success = true;
        List<ValidationError> errors = new ArrayList<>();
        String name = userRequest.getName();
        String password = userRequest.getPassword();
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher matcher = pattern.matcher(name);
            if (name.length() < 5 || name.length() > 50) {
                errors.add(ValidationError.WRONG_USER_NAME_LENGTH);
                log.error("validateUser . user name:'{}' length should be between 5 and 50 included", name);
            }
            if (!matcher.matches()) {
                errors.add(ValidationError.WRONG_USER_NAME_SYMBOLS);
                log.error("validateUser . user name:'{}' should be only Latin symbols and digits only ", name);
            }
            if (password.length() < 8 || password.length() > 100) {
                errors.add(ValidationError.WRONG_USER_PASSWORD_LENGTH);
                log.error("validateUser . user password:'{}' length should be between 8 and 100 included", password);
            }
            if(errors.size() > 0){
                success = false;
                log.error("validateUser . user data is incorrect:'{}'", userRequest);
            }
        return new ValidateResponse(success, errors);
    }
}
