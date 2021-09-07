package ua.goit.goitnotes.validation;

import ua.goit.goitnotes.user.model.User;

public interface Validate {
    ValidateResponse validateNote(ValidateNoteRequest noteRequest, User currentUser);
    ValidateResponse validateUser(ValidateUserRequest userRequest);
}
