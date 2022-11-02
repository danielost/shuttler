package ua.nure.danielost.shuttler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.exception.NoSuchVehicleException;
import ua.nure.danielost.shuttler.service.AdminService;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.repository.RouteRepository;
import ua.nure.danielost.shuttler.repository.UserRepository;
import ua.nure.danielost.shuttler.repository.VehicleRepository;

import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void deleteRoute(long id) throws NoSuchRouteException {
        Optional<Route> foundRoute = routeRepository.findById(id);
        if (!foundRoute.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }

        Route route = foundRoute.get();
        route.getUsers().forEach(user ->
                user.getSavedRoutes().remove(route)
        );

        userRepository.saveAll(route.getUsers());
        routeRepository.deleteById(id);
    }

    @Override
    public void deleteVehicle(String vin) throws NoSuchVehicleException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vin);
        if (!vehicleOptional.isPresent()) {
            throw new NoSuchVehicleException("No vehicles with " + vin + " vin code in database");
        }
        vehicleRepository.delete(vehicleOptional.get());
    }

    @Override
    public void deleteUser(long id) throws NoSuchUserException {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }

        userRepository.deleteById(id);
    }
}
