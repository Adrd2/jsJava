package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

@RestController
@RequestMapping()
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "users/login";
    }

    @GetMapping("user")
    public ModelAndView showUserInfo(@AuthenticationPrincipal User usercom) {
        User user = userService.findUserById(usercom.getId());
        ModelAndView modelAndView = new ModelAndView("users/MainPageUser");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
}
