package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;

import java.util.List;

public interface VehicleService {

    Vehicle addVehicle(Vehicle vehicle, long routeId, long userId) throws NoSuchUserException, NoSuchRouteException, InvalidIdException, InvalidVinCodeException;

    List<Vehicle> getAll() throws EmptyTableException;

    Vehicle getByVin(String vin) throws NoSuchVehicleException;

    void updateVehicle(Vehicle vehicle, String id, long route_id) throws NoSuchVehicleException, InvalidIdException;

    void deleteVehicle(String vin) throws NoSuchVehicleException, InvalidIdException;

    Route getRoute(String vin) throws NoSuchVehicleException;

    void increaseAmountOfPassengers(String vin) throws NoSuchVehicleException;

    void decreaseAmountOfPassengers(String vin) throws NoSuchVehicleException;

    List<Vehicle> getVehiclesByUserId(long userId) throws InvalidIdException;
}
