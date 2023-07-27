package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.models.User;

import java.sql.*;
import java.util.*;

@Component
public class UserDao {


    //    @Value("${spring.datasource.url}")
    private static String URL = "jdbc:mysql://localhost:3306/springbootDemo";

//    @Value("${spring.datasource.username}")
    private static String account = "root";

//    @Value("${spring.datasource.password}")
    private static String password = "827193++";

    @Value("${spring.datasource.driver-class-name}")
    private static String clas;

    private static Connection connection;

    static {

        try {
            System.out.println("Создание коннекта");
            connection = DriverManager.getConnection(URL, account, password);
            System.out.println("коннект создан");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getUser() {
        List<User> userList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "Select * from users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getInt("age"));
                user.setRole(resultSet.getString("role"));
                user.setPassword(resultSet.getString("password"));

                userList.add(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    public User showByID(int id) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM users WHERE id=?"
            );

            preparedStatement.setInt(1,id);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();

            user = new User();

            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setLastName(rs.getString("lastname"));
            user.setAge(rs.getInt("age"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return user;
    }

    public void save(User user) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?,?)");

            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setInt(4, user.getAge());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getRole());




            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(int id, User user) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "UPDATE users SET name=?, lastname=?, age=?, password=?, role=? WHERE id=?");

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getRole());
            preparedStatement.setInt(6, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM users WHERE id=?"
            );

            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
