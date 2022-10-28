package ua.nure.danielost.shuttler.model;

import javax.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;
    private int max_capacity;
    private int current_capacity;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private Route route;

    public Vehicle() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMax_capacity() {
        return max_capacity;
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = max_capacity;
    }

    public int getCurrent_capacity() {
        return current_capacity;
    }

    public void setCurrent_capacity(int current_capacity) {
        this.current_capacity = current_capacity;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
