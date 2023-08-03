package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.RolesDAO;
import ru.kata.spring.boot_security.demo.models.Roles;

import java.util.List;

@Service
public class RolesService {

    private final RolesDAO rolesDAO;

    public RolesService(RolesDAO rolesDAO) {
        this.rolesDAO = rolesDAO;
    }

    public List<Roles> listAllRoles() {
        return rolesDAO.listAllRoles();
    }
    public Roles getRole(String userRole) {
        return rolesDAO.getRole(userRole);
    }

    public void addRole(Roles role) {
        rolesDAO.addRole(role);
    }
}
