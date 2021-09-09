package ua.goit.goitnotes.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goit.goitnotes.error_handling.ObjectNotFoundException;
import ua.goit.goitnotes.error_handling.UserAlreadyExistException;
import ua.goit.goitnotes.interfaces.CrudService;
import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.user.repository.UserRepository;
import ua.goit.goitnotes.user.role.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements CrudService<User> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(String.format("Object 'user' with name %s not found", name));
        }
        return user.get();
    }

    @Override
    public User findById(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isEmpty()) {
            throw new ObjectNotFoundException(String.format("Object 'user' with ID %s not found", uuid));
        }
        return user.get();
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        User userForCreate = prepareUser(user);
        return userRepository.save(userForCreate);
    }

    @Override
    public User update(User user) {
        User userForUpdate = prepareUser(user);
        return userRepository.save(userForUpdate);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public boolean isUserNamePresent(String name) {
        return userRepository.findByName(name).isPresent();
    }

    private User prepareUser(User user) {
        if (isUserNamePresent(user.getName())) {
            throw new UserAlreadyExistException(
                    String.format("User with specified email already exist %s", user.getName()));
        }
        if (roleRepository.findByName("ROLE_USER").isPresent()) {
            user.setUserRole(roleRepository.findByName("ROLE_USER").get());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }
}
