package ua.nure.danielost.shuttler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.model.VehicleType;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {

     Route findByNumber(int number);

     List<Route> findByType(VehicleType type);

     List<Route> findByCreator(User user);
}