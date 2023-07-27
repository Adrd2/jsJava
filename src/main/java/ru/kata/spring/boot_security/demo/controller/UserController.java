package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserDao;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printUser(Model modelmap) {
        modelmap.addAttribute("userMessages", userService.getUser());
        return "user";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model modelMap) {
        System.out.println("SHOW BY ID");
        modelMap.addAttribute("user", userService.showByID(id));
        return "show";
    }

    @GetMapping("/new")
    public String newUser(Model modelMap) {
        System.out.println("NEW USER");
        modelMap.addAttribute("user", new User());
        return "new";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/users/";
    }

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
//        userService.deleteNull();
        System.out.println("updatecom");
        return "redirect:/users/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.delete(id);
//        userService.deleteNull();
        System.out.println("deletecom");
        return "redirect:/users/";
    }
}
