package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.models.Roles;

public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles getRoleByName(String name);
}
