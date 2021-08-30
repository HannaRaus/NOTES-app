package ua.goit.goitnotes.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.goit.goitnotes.exception.UserAlreadyExistException;
import ua.goit.goitnotes.model.entity.User;
import ua.goit.goitnotes.model.repository.RoleRepository;
import ua.goit.goitnotes.model.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserService implements ServiceInterface<User>{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public void register(User user) {
        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new UserAlreadyExistException(
                    String.format("User with specified email already exist %s", user.getName()));
        }
        user.setUserRole(roleRepository.findByName("ROLE_USER").get());
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User with specified name not found");
        }
    }

    @Override
    public User findById(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("User with specified ID not found");
        }
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }
}
