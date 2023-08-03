package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.models.User;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class RegistrationService {

    private final UserDao userDao;


    @Autowired
    public RegistrationService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void register(User user) {
        user.setRoles(Collections.singleton(new Roles("ROLE_user")));
        userDao.save(user);
    }
}
