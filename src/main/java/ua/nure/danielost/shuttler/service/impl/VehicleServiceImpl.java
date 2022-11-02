package ua.nure.danielost.shuttler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.configuration.SecurityConfig;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.InvalidIdException;
import ua.nure.danielost.shuttler.exception.NoSuchVehicleException;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.security.jwt.repository.RouteRepository;
import ua.nure.danielost.shuttler.security.jwt.repository.UserRepository;
import ua.nure.danielost.shuttler.security.jwt.repository.VehicleRepository;
import ua.nure.danielost.shuttler.service.VehicleService;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Vehicle getByVin(String vin) throws NoSuchVehicleException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vin);
        if (!vehicleOptional.isPresent()) {
            throw new NoSuchVehicleException("No vehicles with " + vin + " vin code in database");
        }
        return vehicleOptional.get();
    }

    @Override
    public void deleteVehicle(String vin) throws NoSuchVehicleException, InvalidIdException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vin);
        if (!vehicleOptional.isPresent()) {
            throw new NoSuchVehicleException("No vehicles with " + vin + " vin code in database");
        }
        String username = SecurityConfig.getUsernameByContext();
        Vehicle vehicle = vehicleOptional.get();
        if (!vehicle.getCreator().getUsername().equals(username)) {
            throw new InvalidIdException("You can delete only your own vehicles");
        }

        vehicleRepository.delete(vehicleOptional.get());
    }

    @Override
    public void increaseAmountOfPassengers(String vin) throws NoSuchVehicleException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vin);
        if (!vehicleOptional.isPresent()) {
            throw new NoSuchVehicleException("No vehicles with " + vin + " vin code in database");
        }
        Vehicle vehicle = vehicleOptional.get();
        vehicle.setCurrent_capacity(vehicle.getCurrent_capacity() + 1);
        vehicleRepository.save(vehicle);
    }

    @Override
    public void decreaseAmountOfPassengers(String vin) throws NoSuchVehicleException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vin);
        if (!vehicleOptional.isPresent()) {
            throw new NoSuchVehicleException("No vehicles with " + vin + " vin code in database");
        }

        Vehicle vehicle = vehicleOptional.get();
        if (vehicle.getCurrent_capacity() > 0) {
            vehicle.setCurrent_capacity(vehicle.getCurrent_capacity() - 1);
            vehicleRepository.save(vehicle);
        }
    }

    @Override
    public Route getRoute(String vin) throws NoSuchVehicleException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vin);
        if (!vehicleOptional.isPresent()) {
            throw new NoSuchVehicleException("No vehicles with " + vin + " vin code in database");
        }
        return vehicleOptional.get().getRoute();
    }

    @Override
    public void updateVehicle(
            Vehicle vehicle,
            String vin,
            long route_id
    ) throws NoSuchVehicleException, InvalidIdException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vin);
        if (!vehicleOptional.isPresent()) {
            throw new NoSuchVehicleException("No vehicles with " + vin + " vin code in database");
        }

        Optional<Route> routeOptional = routeRepository.findById(route_id);
        if (!routeOptional.isPresent()) {
            throw new NoSuchVehicleException("No vehicles with " + vin + " vin code in database");
        }

        Vehicle vehicleToUpdate = vehicleOptional.get();
        Route route = routeOptional.get();

        String username = SecurityConfig.getUsernameByContext();
        if (!vehicleToUpdate.getCreator().getUsername().equals(username) ||
            !route.getCreator().getUsername().equals(username)) {
            throw new InvalidIdException("You can update only your vehicle and only your routes");
        }

        vehicleToUpdate.setRoute(route);
        vehicleToUpdate.setCurrent_capacity(vehicle.getCurrent_capacity());
        vehicleToUpdate.setMax_capacity(vehicle.getMax_capacity());

        vehicleRepository.save(vehicleToUpdate);
    }

    @Override
    public Vehicle addVehicle(
            Vehicle vehicle,
            long routeId,
            long userId
    ) throws InvalidIdException {
        Optional<Route> routeOptional = routeRepository.findById(routeId);
        Optional<User> userOptional = userRepository.findById(userId);
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicle.getVin());
        if (!routeOptional.isPresent() || !userOptional.isPresent()) {
            throw new InvalidIdException("Invalid user or route ID");
        }

        if (vehicleOptional.isPresent()) {
            throw new InvalidIdException("Vehicle with this VIN {" + vehicle.getVin() + "} exists");
        }

        Route route = routeOptional.get();
        String username = SecurityConfig.getUsernameByContext();
        if (!route.getCreator().getUsername().equals(username)) {
            throw new InvalidIdException("You can add vehicles only to your routes");
        }

        vehicle.setRoute(route);
        vehicle.setCreator(userOptional.get());
        return vehicleRepository.save(vehicle);
    }

    @Override
    public List<Vehicle> getAll() throws EmptyTableException {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        if (vehicles.isEmpty()) {
            throw new EmptyTableException("No vehicles in database");
        }
        return vehicles;
    }
}
