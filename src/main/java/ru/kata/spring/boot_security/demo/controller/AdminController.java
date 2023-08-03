package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.DAO.RolesDAO;
import ru.kata.spring.boot_security.demo.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String printUser(Model modelmap) {
        modelmap.addAttribute("userMessages", userService.getUser());
        return "admin/user";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") Long id, Model modelMap) {
        System.out.println("SHOW BY ID");
        modelMap.addAttribute("user", userService.showByID(id));
        return "admin/showForADMIN";
    }

    @GetMapping("/new")
    public String newUser(Model modelMap) {
        System.out.println("NEW USER");
        modelMap.addAttribute("user", new User());
        System.out.println("NEW USER AGAIN");
        return "admin/new";
    }

    @PostMapping()
    public String createNewUser(@ModelAttribute("user") User user) {
        userService.save(user);
        System.out.println("ROLE in createNewUser in Controller - " + user.getRoles());
        return "redirect:/admin/";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model modelMap, @PathVariable("id") Long id) {
        System.out.println("EDIT");
        modelMap.addAttribute("user", userService.showByID(id));
        System.out.println("EDITCOMPLITE");
        return "admin/editForADMIN";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateForAdmin(user);
        System.out.println("updatecom");
        return "redirect:/admin/";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        System.out.println("deletecom");
        return "redirect:/admin/";
    }

}
