package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.service.RolesServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;

@Component
public class DBInit {
    private final UserServiceImpl userServiceImpl;
    private final RolesServiceImpl roleService;

    @Autowired
    public DBInit(UserServiceImpl userServiceImpl, RolesServiceImpl roleService) {
        this.userServiceImpl = userServiceImpl;
        this.roleService = roleService;
    }

    @PostConstruct
    private void dataBaseInit() {
//        Roles roleAdmin = new Roles("ROLE_admin");
//        Roles roleUser = new Roles("ROLE_user");
//        Set<Roles> adminSet = new HashSet<>();
//        Set<Roles> userSet = new HashSet<>();
//
//        roleService.addRole(roleAdmin);
//        roleService.addRole(roleUser);
//
//        adminSet.add(roleAdmin);
//        adminSet.add(roleUser);
//        userSet.add(roleUser);
    }
}
