package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RolesService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.stream.Collectors;


@Controller
@RequestMapping
public class AdminController {


    private final UserService userService;
    private final RolesService rolesService;

    @Autowired
    public AdminController(UserService userService, RolesService rolesService) {
        this.userService = userService;
        this.rolesService = rolesService;
    }

    @GetMapping(value = "/admin")
    public String adminPage(@AuthenticationPrincipal User user, Model modelmap) {
        modelmap.addAttribute("user", user);
        modelmap.addAttribute("roles", rolesService.listAllRoles());
        modelmap.addAttribute("users", userService.getUserList());
        return "admin/MainPageAdmin";
    }

    @GetMapping("/add")
    public String newUserPage(@AuthenticationPrincipal User user,Model modelMap) {
        System.out.println("Новый юзер вызов");
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("roles", rolesService.listAllRoles());
        return "admin/newUser";
    }


    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user) {
        getUserRoles(user);
        userService.save(user);
        return "redirect:/admin";
    }

    @PutMapping("/{id}/update")
    public String update(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", rolesService.listAllRoles());
        getUserRoles(user);
        userService.updateForAdmin(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }

    private void getUserRoles(User user) {
        user.setRoles(user.getRoles().stream()
                .map(role -> rolesService.getRole(role.getUserRole()))
                .collect(Collectors.toSet()));
    }

}
