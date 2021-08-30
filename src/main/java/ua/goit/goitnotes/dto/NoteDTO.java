package ua.goit.goitnotes.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

public class NoteDTO {
    @Getter
    @Setter
    private UUID id;
    @Getter
    @Setter
    private String title;
    @Getter
    @Setter
    private String content;
    @Getter
    @Setter
    private String accessType;
    @Getter
    @Setter
    private String userName;

    public NoteDTO(UUID id, String title, String content, String accessType, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.accessType = accessType;
        this.userName = userName;
    }

    public NoteDTO() {
    }
}
