package ua.goit.goitnotes.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.goit.goitnotes.model.entity.UserRole;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<UserRole, UUID> {
    Optional<UserRole> findByName(String name);
}
