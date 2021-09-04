package ua.goit.goitnotes.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.goitnotes.user.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
}
