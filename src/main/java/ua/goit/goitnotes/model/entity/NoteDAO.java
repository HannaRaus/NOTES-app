package ua.goit.goitnotes.model.entity;

import lombok.Getter;
import lombok.Setter;
import ua.goit.goitnotes.model.entity.Enums.AccessType;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "notes")
public class NoteDAO {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Getter
    @Setter
    @Column(name = "title")
    private String title;
    @Getter
    @Setter
    @Column(name = "content")
    private String content;
    @Getter
    @Setter
    @Column(name = "access_type")
    private AccessType accessType;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public NoteDAO(UUID id, String title, String content, AccessType accessType, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.accessType = accessType;
        this.user = user;
    }

    public NoteDAO() {
    }
}
