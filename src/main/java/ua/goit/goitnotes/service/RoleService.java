package ua.goit.goitnotes.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.goit.goitnotes.model.entity.UserRole;
import ua.goit.goitnotes.model.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class RoleService implements ServiceInterface<UserRole> {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserRole findByName(String name) {
        Optional<UserRole> role = roleRepository.findByName(name);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new UsernameNotFoundException("Role with specified name not found");
        }
    }

    @Override
    public UserRole findById(UUID uuid) {
        Optional<UserRole> role = roleRepository.findById(uuid);
        if (role.isPresent()) {
            return role.get();
        } else {
            throw new UsernameNotFoundException("Role with specified ID not found");
        }
    }

    @Override
    public List<UserRole> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void create(UserRole entity) {
        roleRepository.save(entity);
    }

    @Override
    public void update(UserRole entity) {
        roleRepository.save(entity);
    }

    @Override
    public void delete(UserRole entity) {
        roleRepository.delete(entity);
    }
}
