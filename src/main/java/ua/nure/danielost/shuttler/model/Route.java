package ua.nure.danielost.shuttler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int number;
    private VehicleType type;

    @JsonIgnore
    @ManyToMany(mappedBy = "savedRoutes")
    private Set<User> users = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    private Set<Vehicle> vehicles = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "route_stop",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "stop_id")
    )
    private Set<Stop> stops = new HashSet<>();

    public enum VehicleType {
        bus,
        tram,
        trolleybus
    }

    public Route() {}

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public Set<Stop> getStops() {
        return stops;
    }

    public void setStops(Set<Stop> stops) {
        this.stops = stops;
    }

    public Set<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getCongestion() {
        Set<Vehicle> routeVehicles = getVehicles();

        if (routeVehicles.isEmpty()) {
            return 0;
        }

        int capacity = 0;
        int passengersAmount = 0;
        for (Vehicle vehicle: routeVehicles) {
            capacity += vehicle.getMax_capacity();
            passengersAmount += vehicle.getCurrent_capacity();
        }

        return passengersAmount * 1.0 / capacity * 100;
    }

    public void addStop(Stop stop) {
        stops.add(stop);
    }
}
