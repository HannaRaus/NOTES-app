package ua.goit.goitnotes;

import ua.goit.goitnotes.note.model.Note;
import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.user.role.UserRole;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TestUtils {

    public static String getString(int count) {
        return "a".repeat(count);
    }

    public static User getCurrentUser() {
        Set<Note> notes = new HashSet<>();
        return new User(UUID.randomUUID(), "UserName", "password",
                new UserRole(UUID.randomUUID(), "ROLE_USER"), notes);
    }
}
