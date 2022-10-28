package ua.nure.danielost.shuttler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.nure.danielost.shuttler.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
