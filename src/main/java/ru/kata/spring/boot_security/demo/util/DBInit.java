package ru.kata.spring.boot_security.demo.util;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RolesServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.Set;

@Component
public class DBInit {
    private final UserServiceImpl userService;
    private final RolesServiceImpl roleService;

    public DBInit(UserServiceImpl userService, RolesServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        Roles roleAdmin = new Roles("ROLE_admin");
        Roles roleUser = new Roles("ROLE_user");
        roleService.saveRole(roleAdmin);
        roleService.saveRole(roleUser);

        User user = new User("123", 1, "user", "userov", 20, "user@mail.ru", Set.of(roleUser));
        User admin = new User("123", 2, "admin", "adminov", 30, "email@mail.ru", Set.of(roleAdmin));
        userService.save(user);
        userService.save(admin);
    }
}
