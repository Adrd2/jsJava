package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RolesServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class AdminController {


    private final UserServiceImpl userServiceImpl;
    private final RolesServiceImpl rolesServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RolesServiceImpl rolesServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.rolesServiceImpl = rolesServiceImpl;
    }

    @GetMapping("/admin")
    public ResponseEntity<?> adminPage(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(userServiceImpl.getUserList(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/deletde")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userServiceImpl.delete(id);
        return ResponseEntity.ok("User deleted");
    }


    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User user) {
                userServiceImpl.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<?> createUser(@RequestBody User user) {
            userServiceImpl.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/add")
    public String newUserPage(@AuthenticationPrincipal User user, Model modelMap) {
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("roles", rolesServiceImpl.listAllRoles());
        return "admin/newUser";
    }

    private void getUserRoles(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> rolesServiceImpl.getRole(role.getUserRole()))
                .collect(Collectors.toSet()));
    }

}
