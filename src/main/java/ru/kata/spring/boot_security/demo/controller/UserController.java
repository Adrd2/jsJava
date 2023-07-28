package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RolesDAO;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RolesDAO rolesDAO;

    @Autowired
    public UserController(UserService userService, RolesDAO rolesDAO) {
        this.userService = userService;
        this.rolesDAO = rolesDAO;
    }


//    @GetMapping("/{id}")
//    public String showById(@PathVariable("id") int id, Model modelMap) {
//        System.out.println("SHOW BY ID");
//        modelMap.addAttribute("user", userService.showByID(id));
//        return "show";
//    }

    @GetMapping("/{id}/edit")
    public String edit(Model modelMap, @PathVariable("id") int id) {
        System.out.println("EDIT");
        modelMap.addAttribute("user", userService.showByID(id));
        System.out.println("EDITCOMPLITE");
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update (@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.update(id, user);
        System.out.println("updatecom");
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
        System.out.println("deletecom");
        return "redirect:/";
    }

    @GetMapping("/")
    public String showUserByIdForUser(Principal principal, Model model) {
        User user = userService.loadUserByUsername(principal);

        model.addAttribute("user", user);

        return "show";
    }
}
