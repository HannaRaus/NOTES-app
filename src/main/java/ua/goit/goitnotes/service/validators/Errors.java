package ua.goit.goitnotes.service.validators;

public enum Errors {

    EMPTY_NOTE_NAME("Name should not be empty"),
    INVALID_NOTE_NAME("Name should be in range of 5-100 symbols"),
    DUPLICATE_NOTE_NAME("Note with defined name already exists"),
    EMPTY_NOTE_CONTENT("Content should not be empty"),
    INVALID_NOTE_CONTENT("Content should be in range of 5-10 000 symbols"),

    PRIVATE_NOTE_ACCESS("Such a note does not exist"),

    EMPTY_USER_NAME("Name should not be empty"),
    INVALID_USER_NAME("Name should be in range of 5-20 symbols"),
    EMPTY_USER_PASSWORD("Password should not be empty"),
    INVALID_USER_PASSWORD("Password should be in range of 5-20 symbols");

    private final String description;

    Errors(String name) {
        this.description = name;
    }
}
