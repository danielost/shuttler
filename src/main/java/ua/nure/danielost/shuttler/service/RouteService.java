package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.model.VehicleType;

import java.util.List;
import java.util.Set;

public interface RouteService {

    void updateRouteNumber(long id, Route route) throws NoSuchRouteException, InvalidIdException;

    void saveRoute(Route route, long id) throws RouteAlreadyExistsException, NoSuchUserException, InvalidIdException;

    Route getRouteById(long id) throws NoSuchRouteException;

    void deleteRoute(long id) throws NoSuchRouteException, InvalidIdException;

    List<Route> getAllRoutes() throws EmptyTableException;

    Route getRouteByNumber(int number) throws NoSuchRouteException;

    Set<Vehicle> getVehicles(long id) throws NoSuchRouteException;

    double getCongestion(long id) throws NoSuchRouteException;

    List<Route> getRoutes(VehicleType type);

    List<Route> getOptimalRoutes(long routeId, long departId, long destinationId) throws NoSuchRouteException, InvalidIdException, NoSubscriptionException;

    List<Route> findByStops(long stopA, long stopB) throws NoSuchStopException;

    List<Route> getRoutesByUserId(long userId) throws InvalidIdException;
}
