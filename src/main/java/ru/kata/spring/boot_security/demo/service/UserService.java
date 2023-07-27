package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }


    public List<User> getUser() {
        return userDao.getUser();
    }

    public User showByID(int id) {
        return userDao.showByID(id);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void update(int id, User user) {
        userDao.update(id, user);
    }

    public void delete(int id) {
        userDao.delete(id);
    }

}
