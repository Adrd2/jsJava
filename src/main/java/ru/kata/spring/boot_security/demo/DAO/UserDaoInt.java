package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.kata.spring.boot_security.demo.models.User;


public interface UserDaoInt {

    @Query("SELECT u FROM User u WHERE u.username = :username")
    User getUserByName(@Param("username") String name);
}
