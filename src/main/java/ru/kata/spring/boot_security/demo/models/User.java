package ru.kata.spring.boot_security.demo.models;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String username;

    @Column(name = "lastname") // без @Column будет поиск столбца last_name из-за регистра - N
    private String lastName;

    private int age;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "usersRoles"
            , joinColumns = @JoinColumn(name = "users_id")
            , inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    private Set<Roles> roles;

    public void AddRolesToUser(Set<Roles> role) {
        if (roles == null) {
            roles = new HashSet<>();
        }
        roles.addAll(role);
        System.out.println(roles.toString() + " ROLES IN ADD ROLES TO USER ");
    }

    public Set<Roles> getRoles() {
        System.out.println("getroles = " + roles);
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
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
        return username;
    }

    public void setName(String name) {
        this.username = name;
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
        this.username = name;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User " +
                ", name " + username
                + " " + lastName +
                "age - " + age;
    }

    public String getFullName() {
        return username + " " + lastName;
    }

    public String showByID() {
        return "This user number " + id + " is " + username + " " + lastName + ", " + age + " years old";
    }

    public String showByName() {
        return "Name: " + username + lastName + ", '\'" +
                "age" + age + ", '\'" +
                "password" + password + ", '\'" +
                "role" + getRoles();
    }


}
