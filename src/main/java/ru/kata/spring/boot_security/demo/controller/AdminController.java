package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RolesServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;
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
    public ResponseEntity<List<User>> getUsers() {
        return new ResponseEntity<>(userServiceImpl.getUserList(),HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Exception> createUser(@RequestBody User user) {
            userServiceImpl.save(user);
            return new ResponseEntity<>(new Exception("User saved"), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        userServiceImpl.delete(id);
        return ResponseEntity.ok("User deleted");
    }


    @PutMapping("/{id}/update")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody User user) {
                userServiceImpl.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/add")
    public String newUserPage(@AuthenticationPrincipal User user, Model modelMap) {
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("roles", rolesServiceImpl.listAllRoles());
        return "admin/newUser"; // Это имя шаблона для HTML страницы
    }

    private void getUserRoles(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> rolesServiceImpl.getRole(role.getUserRole()))
                .collect(Collectors.toSet()));
    }

}
