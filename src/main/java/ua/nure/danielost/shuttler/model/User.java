package ua.nure.danielost.shuttler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String surname;
    private String email;

    @JsonIgnore
    private String password;

    @ManyToMany
    @JoinTable(
            name = "saved_route",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    private Set<Route> savedRoutes = new HashSet<>();

    public User() {}

    public Set<Route> getSavedRoutes() {
        return savedRoutes;
    }

    public void setSavedRoutes(Set<Route> savedRoutes) {
        this.savedRoutes = savedRoutes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void saveRoute(Route route) {
        savedRoutes.add(route);
    }

    public void deleteRoute(Route route) {
        savedRoutes.remove(route);
    }
}
