package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Roles;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RolesDAO {

    @PersistenceContext
    private EntityManager entityManager;


    public Roles getRole(String userRole) {
        return entityManager.createQuery("select r from Roles r where r.name =: userRole", Roles.class)
                .setParameter("userRole", userRole).getSingleResult();
    }

    public List<Roles> listAllRoles() {
        return entityManager.createQuery("select r from Roles r", Roles.class).getResultList();
    }

    @Transactional
    public void addRole(Roles role) {
        entityManager.persist(role);
    }

}
