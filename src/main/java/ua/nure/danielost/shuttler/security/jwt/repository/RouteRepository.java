package ua.nure.danielost.shuttler.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.VehicleType;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

    public Route findByNumber(int number);

    public List<Route> findByType(VehicleType type);
}
