package ua.goit.goitnotes.user.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import ua.goit.goitnotes.user.model.User;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {
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
    @OneToMany(mappedBy = "userRole")
    private Set<User> users;

    public UserRole(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserRole(String name) {
        this.name = name;
    }
}
