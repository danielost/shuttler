package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.exception.NoSuchVehicleException;

public interface AdminService {

    void deleteRoute(long id) throws NoSuchRouteException;

    void deleteVehicle(String vin) throws NoSuchVehicleException;

    void deleteUser(long id) throws NoSuchUserException;
}
