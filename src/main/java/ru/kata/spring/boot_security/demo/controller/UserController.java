package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.DAO.RolesDAO;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{id}/edit")
    public String edit(Model modelMap, @PathVariable("id") Long id) {
        System.out.println("EDIT");
        modelMap.addAttribute("user", userService.showByID(id));
        System.out.println("EDITCOMPLITE");
        return "users/editForUSER";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateForUser(id, user);
        System.out.println("updatecom");
        return "redirect:/user/";
    }

    @GetMapping("/")
    public String showUserByIdForUser(Principal principal, Model model) {
        User user = userService.loadUserByUsername(principal);

        model.addAttribute("user", user);

        System.out.println("getUserByName in CONTROLLER COMPLITE");

        return "users/show";
    }
}
