package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.model.VehicleType;

import java.util.List;
import java.util.Set;

public interface RouteService {

    public void updateRouteNumber(long id, Route route) throws NoSuchRouteException;

    public void saveRoute(Route route, long id) throws RouteAlreadyExistsException, NoSuchUserException;

    public Route getRouteById(long id) throws NoSuchRouteException;

    public void deleteRoute(long id) throws NoSuchRouteException;

    public List<Route> getAllRoutes() throws EmptyTableException;

    public Route getRouteByNumber(int number) throws NoSuchRouteException;

    public Set<Vehicle> getVehicles(long id) throws NoSuchRouteException;

    public double getCongestion(long id) throws NoSuchRouteException;

    public List<Route> getRoutes(VehicleType type);

    public List<Route> getOptimalRoutes(long routeId, long departId, long destinationId) throws NoSuchRouteException, InvalidIdException;

    public List<Route> findByStops(long stopA, long stopB) throws NoSuchStopException;
}
