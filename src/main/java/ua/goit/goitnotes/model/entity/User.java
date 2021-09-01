package ua.goit.goitnotes.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private UserRole userRole;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<NoteDAO> notes;

    public User(UUID id, String name, String password, UserRole userRole, Set<NoteDAO> notes) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
        this.notes = notes;
    }

    public User(String name, String password, UserRole userRole, Set<NoteDAO> notes) {
        this.name = name;
        this.password = password;
        this.userRole = userRole;
        this.notes = notes;
    }
}
