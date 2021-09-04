package ua.goit.goitnotes.user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.goit.goitnotes.exceptions.ObjectNotFoundException;
import ua.goit.goitnotes.exceptions.UserAlreadyExistException;
import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.user.repository.RoleRepository;
import ua.goit.goitnotes.user.repository.UserRepository;
import ua.goit.goitnotes.interfaces.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@org.springframework.stereotype.Service
public class UserService implements Service<User> {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name).orElse(new User());

    }

    @Override
    public User findById(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ObjectNotFoundException("Object 'user' with specified ID not found");
        }
    }

    @Override
    public Set<User> findAll() {
        return new HashSet<>(userRepository.findAll());
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
        if(roleRepository.findByName("ROLE_USER").isPresent()) {
            user.setUserRole(roleRepository.findByName("ROLE_USER").get());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return user;
    }

}
