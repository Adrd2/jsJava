package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.RegistrationService;

@Controller
@RequestMapping("/auth")
public class RegController {
    private final Validator validator;
    private final RegistrationService registrationService;

    @Autowired
    public RegController(Validator validator, RegistrationService registrationService) {
        this.validator = validator;
        this.registrationService = registrationService;
    }


    @GetMapping("/registration")
    public String registrationPage (@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registrationProcess(@ModelAttribute("user") @Validated User user,
                                      BindingResult bindingResult) {
        validator.validate(user, bindingResult);
        registrationService.register(user);


        if(bindingResult.hasErrors()) {
            return "/auth/registration";
        }

        return "redirect:/";

    }


}
