package ua.goit.goitnotes.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ua.goit.goitnotes.user.model.User;

import java.util.Optional;
import java.util.UUID;
@Component
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByName(String name);
}
