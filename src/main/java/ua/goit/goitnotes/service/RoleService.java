package ua.goit.goitnotes.service;

import ua.goit.goitnotes.exception.ObjectNotFoundException;
import ua.goit.goitnotes.model.entity.UserRole;
import ua.goit.goitnotes.model.repository.RoleRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@org.springframework.stereotype.Service
public class RoleService implements Service<UserRole> {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserRole findByName(String name) {
        Optional<UserRole> role = roleRepository.findByName(name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new ObjectNotFoundException("Object 'role' with specified name not found");
        }
    }

    @Override
    public UserRole findById(UUID uuid) {
        Optional<UserRole> role = roleRepository.findById(uuid);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new ObjectNotFoundException("Object 'role' with specified ID not found");
        }
    }

    @Override
    public Set<UserRole> findAll() {
        return new HashSet<>(roleRepository.findAll());
    }

    @Override
    public UserRole create(UserRole entity) {
        return roleRepository.save(entity);
    }

    @Override
    public UserRole update(UserRole entity) {
        return roleRepository.save(entity);
    }

    @Override
    public void delete(UUID id) {
        roleRepository.deleteById(id);
    }
}
