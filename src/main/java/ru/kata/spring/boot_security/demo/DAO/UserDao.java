package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.sql.*;
import java.util.*;

@Component
public class UserDao {
    @PersistenceContext
    private EntityManager entityManager;


    public List<User> getUser() {
        return entityManager.createQuery("from User", User.class
        ).getResultList();
    }


    public User showByID(Long id) {
        TypedQuery<User> query = entityManager.createQuery(
                "select user from User user where user.id = :id", User.class
        );
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    @Transactional
    public void save(User user) {
        entityManager.persist(user);
    }

    @Transactional
    public void updateForAdmin(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void updateForUser(User user) {
        entityManager.merge(user);
    }

    @Transactional
    public void delete(Long id) {
        entityManager.remove(showByID(id));
    }

    @Transactional
    public User getUserByName(String name) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.username = :userName", User.class)
                .setParameter("userName", name)
                .setMaxResults(1)
                .getSingleResult();
    }

    public User getUserByEmail(String email) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .setMaxResults(1)
                .getSingleResult();
    }
}
