package ru.kata.spring.boot_security.demo.models;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String userName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "role")
    private String role;

    @Column(name = "age")
    private int age;

    @Column(name="password")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User() {
    }

    public User(String password, int id, String name, String lastName, int age) {
        this.password = password;
        this.id = id;
        this.userName = name;
        this.lastName = lastName;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = "ROLE_" + role;
    }

    @Override
    public String toString() {
        return "User " +
                ", name " + userName
                + " " + lastName +
                "age - " + age;
    }

    public String getFullName() {
        return userName + " " + lastName;
    }
    public String showByID() {
        return "This user number " + id + " is " + userName + " " + lastName + ", " + age + " years old";
    }


}
