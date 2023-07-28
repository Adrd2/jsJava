package ru.kata.spring.boot_security.demo.userValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.SecurityUserService;

@Component
public class Validator implements org.springframework.validation.Validator {

    private final SecurityUserService securityUserService;

    @Autowired
    public Validator(SecurityUserService securityUserService) {
        this.securityUserService = securityUserService;
    }




    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            securityUserService.loadUserByUsername(user.getName());
        } catch (UsernameNotFoundException e) {
            return; // обратная проверка, при ошибке - все ок
        }

        errors.rejectValue("username", "", "Имя пользователя занято");
    }
}
