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
    public User findUserById(long id) { // Метод findUserById(long id) - возвращает null , если юзера не найдет
                                        // По логике программы метод не может попасть на несуществующего юзера
        Optional<User> foundUser = usersRepository.findById(id);
        return foundUser.orElse(null);
    }



    public void save(User user) { // в предыдущем случае замудрил описывая невозможное по логике программы исключение
        usersRepository.save(user);
    }

    public void update(User user) {
        usersRepository.save(user);
    }


    public void delete(long id) {
        usersRepository.deleteById(id);
    }

}
