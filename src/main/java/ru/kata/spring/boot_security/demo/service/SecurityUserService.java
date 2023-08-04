package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.DAO.UserDao;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;


@Service
public class SecurityUserService implements UserDetailsService {
    private final UserDao userDao;
    private final UsersRepository usersRepository;

    public SecurityUserService(UserDao userDao, UsersRepository usersRepository) {
        this.userDao = userDao;
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = usersRepository.findByEmail(name);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
