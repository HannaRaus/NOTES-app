package ua.goit.goitnotes.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.goit.goitnotes.exeptions.ObjectNotFoundException;
import ua.goit.goitnotes.exeptions.UserAlreadyExistException;
import ua.goit.goitnotes.model.entity.User;
import ua.goit.goitnotes.model.repository.RoleRepository;
import ua.goit.goitnotes.model.repository.UserRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@org.springframework.stereotype.Service
public class UserService implements Service<User>{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    public void register(User user) {
        if (isUserNamePresent(user.getName())) {
            throw new UserAlreadyExistException(
                    String.format("User with specified email already exist %s", user.getName()));
        }
        if(roleRepository.findByName("ROLE_USER").isPresent()) {
            user.setUserRole(roleRepository.findByName("ROLE_USER").get());
        }
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User findByName(String name) {
        Optional<User> user = userRepository.findByName(name);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ObjectNotFoundException("Object 'user' with specified name not found");
        }
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

        if (userRepository.findByName(user.getName()).isPresent()) {
            throw new UserAlreadyExistException(
                    String.format("User with specified email already exist %s", user.getName()));
        }
        register(user);
        return userRepository.findByName(user.getName()).get();
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        userRepository.deleteById(id);
    }

    public boolean isUserNamePresent(String name) {
        return userRepository.findByName(name).isPresent();
    }

}
