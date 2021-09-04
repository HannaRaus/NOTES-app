package ua.goit.goitnotes.note.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.goit.goitnotes.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "notes")
@Getter
@Setter
@NoArgsConstructor
public class NoteDAO {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "access_type")
    @Enumerated(EnumType.STRING)
    private AccessType accessType;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public NoteDAO(UUID id, String title, String content, AccessType accessType, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.accessType = accessType;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NoteDAO noteDAO)) return false;
        return id.equals(noteDAO.id) && title.equals(noteDAO.title) && content.equals(noteDAO.content) && accessType == noteDAO.accessType && user.equals(noteDAO.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content, accessType);
    }
}
