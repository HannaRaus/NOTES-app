package ua.goit.goitnotes.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserRole(UUID id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

}
