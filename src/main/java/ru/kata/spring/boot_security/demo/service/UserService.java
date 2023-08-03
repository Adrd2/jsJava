package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.DAO.UserDao;

import java.security.Principal;
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

    public User showByID(Long id) {
        return userDao.showByID(id);
    }

    public void save(User user) {
        userDao.save(user);
    }

    public void updateForAdmin(User user) {
        userDao.updateForAdmin(user);
    }

    public void updateForUser(int id, User user) {
        userDao.updateForUser(user);
    }

    public void delete(Long id) {
        userDao.delete(id);
    }


    public User loadUserByUsername(Principal principal) throws UsernameNotFoundException {
        System.out.println("PRINCIPAL GET NAME " + principal.getName());
        User user = userDao.getUserByName(principal.getName());
        System.out.println("getUserByName in SERVICE COMPLITE");
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
