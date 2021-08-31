package ua.goit.goitnotes.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NoteDTO {
    private UUID id;
    private String title;
    private String content;
    private String accessType;
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
