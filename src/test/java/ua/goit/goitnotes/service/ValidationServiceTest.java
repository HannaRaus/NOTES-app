package ua.goit.goitnotes.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.goit.goitnotes.TestUtils;
import ua.goit.goitnotes.config.CustomTestConfiguration;
import ua.goit.goitnotes.enums.AccessType;
import ua.goit.goitnotes.validation.ValidateNoteRequest;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidateUserRequest;
import ua.goit.goitnotes.validation.ValidationError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomTestConfiguration.class)
class ValidationServiceTest {
    @Autowired
    ValidationService validationService;

    @Test
    void validateNote_NoErrors() {
        ValidateNoteRequest noteRequestPrivate = new ValidateNoteRequest("Title", "Content", AccessType.PRIVATE);
        ValidateNoteRequest noteRequestPublic = new ValidateNoteRequest("Title", "Content", AccessType.PUBLIC);
        ValidateResponse responsePrivate = validationService.validateNote(noteRequestPrivate);
        ValidateResponse responsePublic = validationService.validateNote(noteRequestPublic);
        assertEquals(0, responsePrivate.getErrors().size());
        assertTrue(responsePrivate.isSuccess());
        assertEquals(0, responsePublic.getErrors().size());
        assertTrue(responsePublic.isSuccess());
    }

    @Test
    void validateNote_WrongTitleLength() {
        ValidateNoteRequest noteRequestShort = new ValidateNoteRequest("Tit", "Content", AccessType.PRIVATE);
        ValidateNoteRequest noteRequestEmpty = new ValidateNoteRequest("", "Content", AccessType.PRIVATE);
        ValidateNoteRequest noteRequestLong = new ValidateNoteRequest
                (TestUtils.get101CharString(),
                        "Content", AccessType.PRIVATE);
        ValidateResponse responseShort = validationService.validateNote(noteRequestShort);
        ValidateResponse responseLong = validationService.validateNote(noteRequestLong);
        ValidateResponse responseEmpty = validationService.validateNote(noteRequestEmpty);
        assertEquals(1, responseShort.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_TITLE_LENGTH, responseShort.getErrors().get(0));
        assertFalse(responseShort.isSuccess());
        assertEquals(1, responseLong.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_TITLE_LENGTH, responseLong.getErrors().get(0));
        assertFalse(responseLong.isSuccess());
        assertEquals(1, responseEmpty.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_TITLE_LENGTH, responseEmpty.getErrors().get(0));
        assertFalse(responseEmpty.isSuccess());
    }

    @Test
    void validateNote_WrongContentLength() {
        ValidateNoteRequest noteRequestEmpty = new ValidateNoteRequest("Title", "", AccessType.PRIVATE);
        ValidateNoteRequest noteRequestShort = new ValidateNoteRequest("Title", "Cont", AccessType.PRIVATE);
        ValidateNoteRequest noteRequestLong = new ValidateNoteRequest
                ("Title",
                        TestUtils.get10001CharString(), AccessType.PRIVATE);
        ValidateResponse responseShort = validationService.validateNote(noteRequestShort);
        ValidateResponse responseLong = validationService.validateNote(noteRequestLong);
        ValidateResponse responseEmpty = validationService.validateNote(noteRequestEmpty);
        assertEquals(1, responseShort.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_CONTENT_LENGTH, responseShort.getErrors().get(0));
        assertEquals(1, responseLong.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_CONTENT_LENGTH, responseLong.getErrors().get(0));
        assertEquals(1, responseEmpty.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_CONTENT_LENGTH, responseEmpty.getErrors().get(0));
    }

    @Test
    void validateNote_NoAccessType() {
        ValidateNoteRequest noteRequest = new ValidateNoteRequest("Title", "Content", null);

        ValidateResponse response = validationService.validateNote(noteRequest);
        assertEquals(1, response.getErrors().size());
        assertEquals(ValidationError.NOTE_ACCESS_TYPE_IS_NOT_CHOSEN, response.getErrors().get(0));
        assertFalse(response.isSuccess());
    }

    @Test
    void validateUser_NoErrors() {
        ValidateUserRequest validateUserRequest = new ValidateUserRequest("User1", "password");
        ValidateResponse response = validationService.validateUser(validateUserRequest);
        assertEquals(0, response.getErrors().size());
        assertTrue(response.isSuccess());
    }

    @Test
    void validateUser_WrongNameLength() {
        ValidateUserRequest validateUserRequestShort = new ValidateUserRequest("User", "password");
        ValidateUserRequest validateUserRequestLong = new ValidateUserRequest(TestUtils.get51CharString(), "password");
        ValidateResponse responseShort = validationService.validateUser(validateUserRequestShort);
        ValidateResponse responseLong = validationService.validateUser(validateUserRequestLong);
        assertEquals(1, responseShort.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_LENGTH, responseShort.getErrors().get(0));
        assertFalse(responseShort.isSuccess());
        assertEquals(1, responseLong.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_LENGTH, responseLong.getErrors().get(0));
        assertFalse(responseLong.isSuccess());
    }

    @Test
    void validateUser_WrongPasswordLength() {
        ValidateUserRequest validateUserRequestShort = new ValidateUserRequest("User5", "pass");
        ValidateUserRequest validateUserRequestEmpty = new ValidateUserRequest("User5", "");
        ValidateUserRequest validateUserRequestLong = new ValidateUserRequest("User5", TestUtils.get101CharString());
        ValidateResponse responseShort = validationService.validateUser(validateUserRequestShort);
        ValidateResponse responseLong = validationService.validateUser(validateUserRequestLong);
        ValidateResponse responseEmpty = validationService.validateUser(validateUserRequestEmpty);
        assertEquals(1, responseShort.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_PASSWORD_LENGTH, responseShort.getErrors().get(0));
        assertFalse(responseShort.isSuccess());
        assertEquals(1, responseLong.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_PASSWORD_LENGTH, responseLong.getErrors().get(0));
        assertFalse(responseLong.isSuccess());
        assertEquals(1, responseEmpty.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_PASSWORD_LENGTH, responseEmpty.getErrors().get(0));
        assertFalse(responseLong.isSuccess());
    }

    @Test
    void validateUser_WrongUserNameSymbols() {
        ValidateUserRequest validateUserRequestSpaceMiddle = new ValidateUserRequest("User 5sf", "password");
        ValidateUserRequest validateUserRequestSpaceStart = new ValidateUserRequest(" User5sf", "password");
        ValidateUserRequest validateUserRequestSpaceEnd = new ValidateUserRequest("User5sf ", "password");
        ValidateUserRequest validateUserRequestSymbolEnd = new ValidateUserRequest("User5%", "password");
        ValidateUserRequest validateUserRequestSymbolStart = new ValidateUserRequest(";!User5", "password");
        ValidateUserRequest validateUserRequestSymbolMiddle = new ValidateUserRequest("Us#er5", "password");
        ValidateUserRequest validateUserRequestSymbolNotLatinEnd = new ValidateUserRequest("User5Ц", "password");
        ValidateUserRequest validateUserRequestSymbolNotLatinStart = new ValidateUserRequest("ЩUser5", "password");
        ValidateUserRequest validateUserRequestSymbolNotLatinMiddle = new ValidateUserRequest("UseИr5", "password");
        ValidateResponse responseSpaceMiddle = validationService.validateUser(validateUserRequestSpaceMiddle);
        ValidateResponse responseSpaceStart = validationService.validateUser(validateUserRequestSpaceStart);
        ValidateResponse responseSpaceEnd = validationService.validateUser(validateUserRequestSpaceEnd);
        ValidateResponse responseSymbolEnd = validationService.validateUser(validateUserRequestSymbolEnd);
        ValidateResponse responseSymbolMiddle = validationService.validateUser(validateUserRequestSymbolMiddle);
        ValidateResponse responseSymbolStart = validationService.validateUser(validateUserRequestSymbolStart);
        ValidateResponse responseNotLatinEnd = validationService.validateUser(validateUserRequestSymbolNotLatinEnd);
        ValidateResponse responseNotLatinStart = validationService.validateUser(validateUserRequestSymbolNotLatinStart);
        ValidateResponse responseNotLatinMiddle = validationService.validateUser(validateUserRequestSymbolNotLatinMiddle);
        assertEquals(1, responseSpaceEnd.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseSpaceEnd.getErrors().get(0));
        assertFalse(responseSpaceEnd.isSuccess());
        assertEquals(1, responseSpaceMiddle.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseSpaceMiddle.getErrors().get(0));
        assertFalse(responseSpaceMiddle.isSuccess());
        assertEquals(1, responseSpaceStart.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseSymbolStart.getErrors().get(0));
        assertFalse(responseSpaceStart.isSuccess());
        assertEquals(1, responseSymbolEnd.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseSymbolEnd.getErrors().get(0));
        assertFalse(responseSpaceStart.isSuccess());
        assertEquals(1, responseSymbolMiddle.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseSymbolMiddle.getErrors().get(0));
        assertFalse(responseSpaceStart.isSuccess());
        assertEquals(1, responseSymbolStart.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseSymbolStart.getErrors().get(0));
        assertFalse(responseSpaceStart.isSuccess());
        assertEquals(1, responseNotLatinEnd.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseNotLatinEnd.getErrors().get(0));
        assertFalse(responseSpaceStart.isSuccess());
        assertEquals(1, responseNotLatinMiddle.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseNotLatinMiddle.getErrors().get(0));
        assertFalse(responseSpaceStart.isSuccess());
        assertEquals(1, responseNotLatinStart.getErrors().size());
        assertEquals(ValidationError.WRONG_USER_NAME_SYMBOLS, responseNotLatinStart.getErrors().get(0));
        assertFalse(responseSpaceStart.isSuccess());
    }
}