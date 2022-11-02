package ua.nure.danielost.shuttler.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(
            name = "saved_route",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id")
    )
    private Set<Route> savedRoutes = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private List<Role> roles = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "user_subscriptions",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    )
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private Set<Route> createdRoutes = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "creator")
    private Set<Vehicle> ownedVehicles = new HashSet<>();

    public User() {}

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public Set<Vehicle> getOwnedVehicles() {
        return ownedVehicles;
    }

    public void setOwnedVehicles(Set<Vehicle> ownedVehicles) {
        this.ownedVehicles = ownedVehicles;
    }

    public Set<Route> getCreatedRoutes() {
        return createdRoutes;
    }

    public void setCreatedRoutes(Set<Route> createdRoutes) {
        this.createdRoutes = createdRoutes;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
