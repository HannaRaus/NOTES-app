package ua.goit.goitnotes.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ua.goit.goitnotes.exeptions.ObjectNotFoundException;
import ua.goit.goitnotes.model.entity.User;
import ua.goit.goitnotes.model.entity.UserRole;
import ua.goit.goitnotes.service.RoleService;
import ua.goit.goitnotes.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class AppReadyToUseListener {
    @Value("${default.admin.name}")
    private String defaultAdminName;

    @Value("${default.admin.password}")
    private String defaultAdminPassword;


    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AppReadyToUseListener(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void appReady() {

        try {
            userService.findByName("admin");
        } catch (ObjectNotFoundException e) {
            log.info(e.getMessage());
        }finally {
            addDefaultRoles();
            addAdminUser();
        }

    }

    private void addDefaultRoles() {

        List<String> userRols = new ArrayList<>() {
            {
                add("ROLE_ADMIN");
                add("ROLE_USER");
            }
        };

        userRols.forEach((r) -> {
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
