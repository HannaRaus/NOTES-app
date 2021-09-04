package ua.goit.goitnotes.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.goit.goitnotes.model.entity.User;
import ua.goit.goitnotes.model.entity.UserRole;
import ua.goit.goitnotes.service.RoleService;
import ua.goit.goitnotes.service.UserService;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AppReadyToUseListener {

    private final UserService userService;
    private final RoleService roleService;

    @Value("${default.admin.name}")
    private String defaultAdminName;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {
        if (userService.findByName(defaultAdminName).getName() != null) {
            return;
        }
        addDefaultRoles();
        addAdminUser();
    }

    private void addDefaultRoles() {

        List<String> userRoles = Arrays.asList("ROLE_ADMIN", "ROLE_USER");

        userRoles.forEach((r) -> {
            UserRole user = new UserRole();
            user.setName(r);
            roleService.create(user);
        });

    }

    private void addAdminUser() {
        User adminUser = new User();
        adminUser.setName(defaultAdminName);
        adminUser.setPassword(defaultAdminPassword);
        adminUser.setUserRole(roleService.findByName("ROLE_ADMIN"));

        userService.create(adminUser);
    }
}
