package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.RolesDAO;
import ru.kata.spring.boot_security.demo.repositories.SecurityUserRepository;

import javax.transaction.Transactional;

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
        user.setRole(new Roles().getDefault());
        securityUserRepository.save(user);
    }
}
