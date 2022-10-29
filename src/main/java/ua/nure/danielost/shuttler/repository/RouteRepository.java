package ua.nure.danielost.shuttler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.danielost.shuttler.model.Route;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    public Route findByNumber(int number);
    public List<Route> findByType(Route.VehicleType type);
}
