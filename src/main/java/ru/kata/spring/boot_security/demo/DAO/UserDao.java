package ru.kata.spring.boot_security.demo.DAO;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Roles;
import ru.kata.spring.boot_security.demo.models.User;

import java.sql.*;
import java.util.*;

    @Component
public class UserDao {

        private static String URL = "jdbc:mysql://localhost:3306/springbootDemo";
        private static String account = "root";
        private static String password = "827193++";
        private static Connection connection;
        String disable = "SET FOREIGN_KEY_CHECKS=0;";
        String activate = "SET FOREIGN_KEY_CHECKS=0;";


        static {

            try {
                System.out.println("Создание коннекта User");
                connection = DriverManager.getConnection(URL, account, password);
                System.out.println("коннект создан User");
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

                preparedStatement.setInt(1, id);

                ResultSet rs = preparedStatement.executeQuery();

                rs.next();

                user = new User();

                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getInt("age"));
                user.setPassword(rs.getString("password"));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            return user;
        }

        public void save(User user) {
            try {
                PreparedStatement preparedStatement =
                        connection.prepareStatement("INSERT INTO users VALUES(?,?,?,?,?)");

                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, user.getName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setInt(4, user.getAge());
                preparedStatement.setString(5, user.getPassword());

                System.out.println(user.getRoles() + "THIS IS getRoles in save in DAO");
                user.AddRolesToUser(user.getRoles());


                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void updateForAdmin(int id, User user) {
            try {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                "UPDATE users SET name=?, lastname=?, age=?, password=? WHERE id=?");

                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setInt(5, id);

                System.out.println(user.getRoles() + "THIS IS getRoles in updateForAdmin in DAO and toString Role" + new Roles().toString());
                user.setRoles(user.getRoles());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void updateForUser(int id, User user) {
            try {
                PreparedStatement preparedStatement =
                        connection.prepareStatement(
                                "UPDATE users SET name=?, lastname=?, age=?, password=? WHERE id=?");

                preparedStatement.setString(1, user.getName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setInt(3, user.getAge());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setInt(5, id);

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public void delete(int id) {
            String diactivate = "SET FOREIGN_KEY_CHECKS=OFF;";
            String activate = "SET FOREIGN_KEY_CHECKS=ON;";
            PreparedStatement preparedStatement = null;
            PreparedStatement prDiactivate = null;
            PreparedStatement prActivate = null;
            try {
                preparedStatement = connection.prepareStatement(
                        "DELETE FROM users WHERE id=?"
                );
                prDiactivate = connection.prepareStatement(diactivate);
                prDiactivate.execute();

                preparedStatement.setInt(1, id);

                preparedStatement.executeUpdate();

                prActivate = connection.prepareStatement(diactivate);
                prActivate.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        public User getUserByName(String name) {
            PreparedStatement preparedStatement;
            User user;
            try {
                preparedStatement = connection.prepareStatement(
                        "SELECT * FROM users WHERE name=?;"
                );
                preparedStatement.setString(1, name);

                user = new User();

                ResultSet rs = preparedStatement.executeQuery();
                System.out.println(rs);
                System.out.println("RESULT SET NAME " + name);
                rs.next();
                System.out.println("ResultSet is started");


                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getInt("age"));
                user.setPassword(rs.getString("password"));

                System.out.println("getUserByName in DAO COMPLITE");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return user;
        }
    }
