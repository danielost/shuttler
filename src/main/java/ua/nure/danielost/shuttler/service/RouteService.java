package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.RouteAlreadyExistsException;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;

import java.util.List;
import java.util.Set;

public interface RouteService {
    public long updateRouteNumber(long id, Route route) throws NoSuchRouteException;
    public Route saveRoute(Route route) throws RouteAlreadyExistsException;
    public Route getRouteById(long id) throws NoSuchRouteException;
    public long deleteRoute(long id) throws NoSuchRouteException;
    public List<Route> getAllRoutes() throws EmptyTableException;
    public Route getRouteByNumber(int number) throws NoSuchRouteException;
    public Set<Vehicle> getVehicles(long id) throws NoSuchRouteException;
}
