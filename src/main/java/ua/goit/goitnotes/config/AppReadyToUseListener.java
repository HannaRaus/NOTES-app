package ua.goit.goitnotes.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.goit.goitnotes.error_handling.ObjectNotFoundException;
import ua.goit.goitnotes.user.model.User;
import ua.goit.goitnotes.user.role.RoleService;
import ua.goit.goitnotes.user.role.UserRole;
import ua.goit.goitnotes.user.role.UserRoles;
import ua.goit.goitnotes.user.service.UserService;

import java.util.Arrays;

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
        try {
            userService.findByName(defaultAdminName);
        } catch (ObjectNotFoundException ex) {
            log.debug(ex.getMessage());
        } finally {
            addDefaultRoles();
            addAdminUser();
        }
    }

    private void addDefaultRoles() {

        Arrays.stream(UserRoles.values())
                .forEach((r) -> {
                    UserRole userRole = new UserRole();
                    userRole.setName(r.toString());
                    roleService.create(userRole);
                });

    }

    private void addAdminUser() {
        User adminUser = new User();
        adminUser.setName(defaultAdminName);
        adminUser.setPassword(defaultAdminPassword);
        adminUser.setUserRole(roleService.findByName(UserRoles.ROLE_ADMIN.name()));

        userService.create(adminUser);
    }
}
