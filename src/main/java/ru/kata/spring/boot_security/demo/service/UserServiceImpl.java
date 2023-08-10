package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repository.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UsersRepository usersRepository;

    public UserServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }


    public List<User> getUserList() {
        return usersRepository.findAll();
    }

    @Override
    public User findUserById(long id) {
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElse(null);
    }



    public void save(User user) {
        if (user.getId() == 0) {
            user.setPassword(user.getPassword());
        } else {
            if (user.getPassword().equals("")) {
                user.setPassword(findUserById(user.getId()).getPassword());
            } else {
                user.setPassword(user.getPassword());
            }
        }
        usersRepository.save(user);
    }

    public void update(User user) {
        usersRepository.save(user);
    }


    public void delete(long id) {
        usersRepository.deleteById(id);
    }

}
