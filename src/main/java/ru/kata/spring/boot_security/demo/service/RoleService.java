package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Roles;

import java.util.List;

public interface RoleService {

    List<Roles> listAllRoles();

    void saveRole(Roles role);

    void deleteRole(Long id);

    Roles getRole(String name);
}
