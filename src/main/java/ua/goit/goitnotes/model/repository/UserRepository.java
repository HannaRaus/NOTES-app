package ua.goit.goitnotes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.goitnotes.model.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
}
