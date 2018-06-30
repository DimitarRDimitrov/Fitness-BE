package com.example.demo.entity;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Role")
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "name")
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<User> users;

    public Role() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
