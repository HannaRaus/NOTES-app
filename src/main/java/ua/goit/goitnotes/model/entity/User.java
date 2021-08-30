package ua.goit.goitnotes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Getter
    @Setter
    @Column(name = "name")
    private String name;
    @Getter
    @Setter
    @Column(name = "password")
    private String password;
    @Getter
    @Setter
    @OneToOne(mappedBy = "users")
    private UserRole userRole;
    @Getter
    @Setter
    @OneToMany(mappedBy = "users")
    private Set<NoteDAO> notes;

    public User(UUID id, String name, String password, UserRole userRole, Set<NoteDAO> notes) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userRole = userRole;
        this.notes = notes;
    }

    public User() {
    }
}
