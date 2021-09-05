package ua.goit.goitnotes.note.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.goit.goitnotes.TestUtils;
import ua.goit.goitnotes.note.dto.NoteDTO;
import ua.goit.goitnotes.user.service.UserService;
import ua.goit.goitnotes.validation.ValidateNoteRequest;
import ua.goit.goitnotes.validation.ValidateResponse;
import ua.goit.goitnotes.validation.ValidateUserRequest;
import ua.goit.goitnotes.validation.ValidationError;
import ua.goit.goitnotes.validation.ValidationService;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ValidationServiceTest {

    @MockBean
    private NoteService noteService;

    @MockBean
    private UserService userService;

    @InjectMocks
    private ValidationService validationService;

    private String stringId = "1162d158-0bf0-11ec-9a03-0242ac130003";
    private UUID randomId = UUID.fromString(stringId);

    @Test
    void validateNote_NoErrors() {
        when(noteService.isTitlePresetForTheUser(anyString(), any())).thenReturn(false);
        ValidateNoteRequest noteRequestPrivate = new ValidateNoteRequest("Title", "Content", "PRIVATE", stringId);
        ValidateNoteRequest noteRequestPublic = new ValidateNoteRequest("Title", "Content", "PUBLIC", stringId);
        ValidateResponse responsePrivate = validationService.validateNote(noteRequestPrivate, TestUtils.getCurrentUser());
        ValidateResponse responsePublic = validationService.validateNote(noteRequestPublic, TestUtils.getCurrentUser());
        assertEquals(0, responsePrivate.getErrors().size());
        assertTrue(responsePrivate.isSuccess());
        assertEquals(0, responsePublic.getErrors().size());
        assertTrue(responsePublic.isSuccess());
    }

    @Test
    void validateNote_WrongTitleLength() {
        when(noteService.isTitlePresetForTheUser(anyString(), any())).thenReturn(false);
        ValidateNoteRequest noteRequestShort = new ValidateNoteRequest("Tit", "Content", "PRIVATE", stringId);
        ValidateNoteRequest noteRequestEmpty = new ValidateNoteRequest("", "Content", "PRIVATE", stringId);
        ValidateNoteRequest noteRequestLong = new ValidateNoteRequest
                (TestUtils.getString(101),
                        "Content", "PRIVATE", stringId);
        ValidateResponse responseShort = validationService.validateNote(noteRequestShort, TestUtils.getCurrentUser());
        ValidateResponse responseLong = validationService.validateNote(noteRequestLong, TestUtils.getCurrentUser());
        ValidateResponse responseEmpty = validationService.validateNote(noteRequestEmpty, TestUtils.getCurrentUser());
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
        when(noteService.isTitlePresetForTheUser(anyString(), any())).thenReturn(false);
        ValidateNoteRequest noteRequestEmpty = new ValidateNoteRequest("Title", "", "PRIVATE", stringId);
        ValidateNoteRequest noteRequestShort = new ValidateNoteRequest("Title", "Cont", "PRIVATE", stringId);
        ValidateNoteRequest noteRequestLong = new ValidateNoteRequest
                ("Title", TestUtils.getString(10001), "PRIVATE", stringId);
        ValidateResponse responseShort = validationService.validateNote(noteRequestShort, TestUtils.getCurrentUser());
        ValidateResponse responseLong = validationService.validateNote(noteRequestLong, TestUtils.getCurrentUser());
        ValidateResponse responseEmpty = validationService.validateNote(noteRequestEmpty, TestUtils.getCurrentUser());
        assertEquals(1, responseShort.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_CONTENT_LENGTH, responseShort.getErrors().get(0));
        assertEquals(1, responseLong.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_CONTENT_LENGTH, responseLong.getErrors().get(0));
        assertEquals(1, responseEmpty.getErrors().size());
        assertEquals(ValidationError.WRONG_NOTE_CONTENT_LENGTH, responseEmpty.getErrors().get(0));
    }

    @Test
    void validateNote_WrongAccessType() {
        ValidateNoteRequest noteRequest = new ValidateNoteRequest("Title", "Content", "Something", stringId);
        ValidateNoteRequest noteRequestEmpty = new ValidateNoteRequest("Title", "Content", "", stringId);
        ValidateResponse response = validationService.validateNote(noteRequest, TestUtils.getCurrentUser());
        ValidateResponse responseEmpty = validationService.validateNote(noteRequestEmpty, TestUtils.getCurrentUser());
        when(noteService.isTitlePresetForTheUser(anyString(), any())).thenReturn(false);
        assertEquals(1, response.getErrors().size());
        assertEquals(ValidationError.WRONG_ACCESS_TYPE, response.getErrors().get(0));
        assertFalse(response.isSuccess());
        assertEquals(1, responseEmpty.getErrors().size());
        assertEquals(ValidationError.WRONG_ACCESS_TYPE, responseEmpty.getErrors().get(0));
        assertFalse(response.isSuccess());
    }

    @Test
    void validateNote_RightAccessType() {
        when(noteService.isTitlePresetForTheUser(anyString(), any())).thenReturn(false);
        ValidateNoteRequest noteRequest = new ValidateNoteRequest("Title", "Content", "PRIVATE", stringId);
        ValidateNoteRequest noteRequestCaseInsensitive = new ValidateNoteRequest("Title", "Content", "PuBlic", stringId);
        ValidateResponse response = validationService.validateNote(noteRequest, TestUtils.getCurrentUser());
        ValidateResponse responseCaseInsensitive = validationService.validateNote(noteRequestCaseInsensitive, TestUtils.getCurrentUser());
        assertEquals(0, response.getErrors().size());
        assertTrue(response.isSuccess());
        assertEquals(0, responseCaseInsensitive.getErrors().size());
        assertTrue(response.isSuccess());
    }

    @Test
    void validateNote_NotUniqueNoteForUserDuringEditingAnotherNote(){
        when(noteService.isTitlePresetForTheUser(anyString(), any())).thenReturn(true);
        when(noteService.findByName(anyString())).thenReturn(
                new NoteDTO(UUID.fromString("1162d158-0bf0-11ec-9a03-0242ac130002"),
                        "title", "content", "PRIVATE", "UserName"));
        ValidateNoteRequest noteRequest = new ValidateNoteRequest("Title", "Content", "PRIVATE", stringId);
        ValidateResponse response = validationService.validateNote(noteRequest, TestUtils.getCurrentUser());
        assertEquals(1, response.getErrors().size());
        assertEquals(ValidationError.NOTE_TITLE_NOT_UNIQUE_FOR_CURRENT_USER, response.getErrors().get(0));
        assertFalse(response.isSuccess());
    }

    @Test
    void validateNote_NotUniqueNoteForUserDuringEditingSameNote_happyPath() {
        when(noteService.isTitlePresetForTheUser(anyString(), any())).thenReturn(true);
        when(noteService.findByName(anyString())).thenReturn(
                new NoteDTO(UUID.fromString(stringId),
                        "title", "content", "PRIVATE", "UserName"));
        ValidateNoteRequest noteRequest = new ValidateNoteRequest("Title", "Content", "PRIVATE", stringId);
        ValidateResponse response = validationService.validateNote(noteRequest, TestUtils.getCurrentUser());
        assertEquals(0, response.getErrors().size());
        assertTrue(response.isSuccess());
    }

    @Test
    void validateUser_NameIsNotUnique(){
        when(userService.isUserNamePresent(anyString())).thenReturn(true);
        ValidateUserRequest validateUserRequest = new ValidateUserRequest("User1", "password");
        ValidateResponse response = validationService.validateUser(validateUserRequest);
        assertEquals(1, response.getErrors().size());
        assertEquals(ValidationError.USER_NAME_NOT_UNIQUE, response.getErrors().get(0));
        assertFalse(response.isSuccess());
    }

    @Test
    void validateUser_NoErrors() {
        when(userService.isUserNamePresent(anyString())).thenReturn(false);
        ValidateUserRequest validateUserRequest = new ValidateUserRequest("User1", "password");
        ValidateResponse response = validationService.validateUser(validateUserRequest);
        assertEquals(0, response.getErrors().size());
        assertTrue(response.isSuccess());
    }

    @Test
    void validateUser_WrongNameLength() {
        when(userService.isUserNamePresent(anyString())).thenReturn(false);
        ValidateUserRequest validateUserRequestShort = new ValidateUserRequest("User", "password");
        ValidateUserRequest validateUserRequestLong = new ValidateUserRequest(TestUtils.getString(51), "password");
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
        when(userService.isUserNamePresent(anyString())).thenReturn(false);
        ValidateUserRequest validateUserRequestShort = new ValidateUserRequest("User5", "pass");
        ValidateUserRequest validateUserRequestEmpty = new ValidateUserRequest("User5", "");
        ValidateUserRequest validateUserRequestLong = new ValidateUserRequest("User5", TestUtils.getString(101));
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
        when(userService.isUserNamePresent(anyString())).thenReturn(false);
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