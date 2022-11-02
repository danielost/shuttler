package ua.nure.danielost.shuttler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.model.Vehicle;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    List<Vehicle> findByCreator(User user);
}
