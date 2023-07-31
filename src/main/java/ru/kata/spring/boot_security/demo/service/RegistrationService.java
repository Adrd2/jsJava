package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.DAO.RolesDAO;
import ru.kata.spring.boot_security.demo.repositories.SecurityUserRepository;

import javax.transaction.Transactional;
import java.util.Collections;

@Service
public class RegistrationService {

    private final RolesDAO rolesDAO;


    private final SecurityUserRepository securityUserRepository;

    @Autowired
    public RegistrationService(RolesDAO rolesDAO, SecurityUserRepository securityUserRepository) {
        this.rolesDAO = rolesDAO;
        this.securityUserRepository = securityUserRepository;
    }

    @Transactional
    public void register(User user) {
        user.setRoles(Collections.singleton(new Roles("ROLE_user")));
        securityUserRepository.save(user);
    }
}
