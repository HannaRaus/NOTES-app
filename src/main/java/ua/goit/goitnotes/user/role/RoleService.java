package ua.goit.goitnotes.user.role;

import org.springframework.stereotype.Service;
import ua.goit.goitnotes.error_handling.ObjectNotFoundException;
import ua.goit.goitnotes.interfaces.CrudService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService implements CrudService<UserRole> {

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
    public List<UserRole> findAll() {
        return roleRepository.findAll();
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
