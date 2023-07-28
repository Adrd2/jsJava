package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.parameters.P;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
public class Roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name", unique = true)
    private String name;


    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinTable(
            name = "usersRoles"
            , joinColumns = @JoinColumn(name = "roles_id")
            , inverseJoinColumns = @JoinColumn(name = "users_id")
    )
    private List<User> users;

    public void addUsersToRoles(User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }
    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Roles() {
    }

    public Roles(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Roles{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public String getDefault() {
        return "ROLE_user";
    }

}
