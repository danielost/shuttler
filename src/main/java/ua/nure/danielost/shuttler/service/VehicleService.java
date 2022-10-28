package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.exception.NoSuchVehicleException;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;

import java.util.List;

public interface VehicleService {
    public Vehicle addVehicle(Vehicle vehicle, long id) throws NoSuchUserException, NoSuchRouteException;
    public List<Vehicle> getAll() throws EmptyTableException;
    public Vehicle getByVin(String vin) throws NoSuchVehicleException;
    public void updateVehicle(Vehicle vehicle, String id, long route_id) throws NoSuchVehicleException;
    public void deleteVehicle(String vin) throws NoSuchVehicleException;
    public Route getRoute(String vin) throws NoSuchVehicleException;
    public void increaseAmountOfPassengers(String vin) throws NoSuchVehicleException;
    public void decreaseAmountOfPassengers(String vin) throws NoSuchVehicleException;

}
