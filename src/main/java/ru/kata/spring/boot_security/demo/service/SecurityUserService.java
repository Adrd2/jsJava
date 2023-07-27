package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.SecurityUserRepository;
import ru.kata.spring.boot_security.demo.security.UserDetailsSecurity;

import java.util.List;
import java.util.Optional;

@Service
public class SecurityUserService implements UserDetailsService {
    private final SecurityUserRepository securityUserRepository;

    public SecurityUserService(SecurityUserRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Optional<User> user = securityUserRepository.findByUserName(name);

        if(user.isEmpty()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return new UserDetailsSecurity(user.get());
    }
}
