package ua.goit.goitnotes.note.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
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
}
