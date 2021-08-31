package ua.goit.goitnotes.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.goit.goitnotes.model.entity.User;
import ua.goit.goitnotes.model.repository.UserRepository;

@Service(value = "userServiceDetails")
public class GoITNotesUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public GoITNotesUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        final User user = userRepository.findByName(userName).orElseThrow(() ->
                new UsernameNotFoundException(String.format("user with username %s not exists", userName)));
        return new UserPrincipal(user);
    }
}
