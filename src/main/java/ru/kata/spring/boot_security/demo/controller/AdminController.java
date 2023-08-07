package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RolesServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.stream.Collectors;


@Controller
@RequestMapping
public class AdminController {


    private final UserServiceImpl userServiceImpl;
    private final RolesServiceImpl rolesServiceImpl;

    @Autowired
    public AdminController(UserServiceImpl userServiceImpl, RolesServiceImpl rolesServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.rolesServiceImpl = rolesServiceImpl;
    }

    @GetMapping(value = "/admin")
    public String adminPage(@AuthenticationPrincipal User user, Model modelmap) {
        modelmap.addAttribute("user", user);
        modelmap.addAttribute("roles", rolesServiceImpl.listAllRoles());
        modelmap.addAttribute("users", userServiceImpl.getUserList());
        return "admin/MainPageAdmin";
    }

    @GetMapping("/add")
    public String newUserPage(@AuthenticationPrincipal User user, Model modelMap) {
        System.out.println("Новый юзер вызов");
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("roles", rolesServiceImpl.listAllRoles());
        return "admin/newUser";
    }


    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        getUserRoles(user);
        userServiceImpl.save(user);
        return "redirect:/admin";
    }

    @PutMapping("/{id}/update")
    public String update(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", rolesServiceImpl.listAllRoles());
        getUserRoles(user);
        userServiceImpl.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userServiceImpl.delete(id);
        return "redirect:/admin/";
    }

    private void getUserRoles(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> rolesServiceImpl.getRole(role.getUserRole()))
                .collect(Collectors.toSet()));
    }

}
