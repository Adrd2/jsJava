package ru.kata.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "roles")
public class Roles implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

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
        return getName();
    }


    @Override
    public String getAuthority() {
        return getName();
    }

    public String getUserRole() {
        return name;
    }
}
