package ua.goit.goitnotes.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @OneToOne(mappedBy = "users")
    private UserRole userRole;
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
