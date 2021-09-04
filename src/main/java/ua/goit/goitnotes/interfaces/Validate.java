package ua.goit.goitnotes.interfaces;

import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.validation.ValidateNoteRequest;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidateUserRequest;

public interface Validate {
    public ValidateResponse validateNote(ValidateNoteRequest noteRequest, User currentUser);
    ValidateResponse validateUser(ValidateUserRequest userRequest);
}
