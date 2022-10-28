package ua.nure.danielost.shuttler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.RouteAlreadyExistsException;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.repository.RouteRepository;
import ua.nure.danielost.shuttler.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    public void updateRouteNumber(long id, Route route) throws NoSuchRouteException {
        Optional<Route> foundRoute = routeRepository.findById(id);
        if (!foundRoute.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }

        Route routeToUpdate = foundRoute.get();
        routeToUpdate.setNumber(route.getNumber());

        routeRepository.save(routeToUpdate);
    }

    @Override
    public void saveRoute(Route route) throws RouteAlreadyExistsException {
        if (routeRepository.findByNumber(route.getNumber()) != null) {
            throw new RouteAlreadyExistsException("Route with number " + route.getNumber() + " already exists");
        }
        routeRepository.save(route);
    }

    @Override
    public Route getRouteById(long id) throws NoSuchRouteException {
        Optional<Route> foundRoute = routeRepository.findById(id);
        if (!foundRoute.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }

        return foundRoute.get();
    }

    @Override
    public void deleteRoute(long id) throws NoSuchRouteException {
        Optional<Route> foundRoute = routeRepository.findById(id);
        if (!foundRoute.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }
        Route route = foundRoute.get();
        route.getUsers().forEach(user -> user.getSavedRoutes().remove(route));
        userRepository.saveAll(route.getUsers());
        routeRepository.deleteById(id);
    }

    @Override
    public Route getRouteByNumber(int number) throws NoSuchRouteException {
        Route foundRoute = routeRepository.findByNumber(number);
        if (foundRoute == null) {
            throw new NoSuchRouteException("No routes with " + number + " number in database");
        }

        return foundRoute;
    }

    @Override
    public Set<Vehicle> getVehicles(long id) throws NoSuchRouteException {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if (!routeOptional.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }
        return routeOptional.get().getVehicles();
    }

    @Override
    public double getCongestion(long id) throws NoSuchRouteException {
        Optional<Route> routeOptional = routeRepository.findById(id);
        if (!routeOptional.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }
        return routeOptional.get().getCongestion();
    }

    @Override
    public List<Route> getAllRoutes() throws EmptyTableException {
        List<Route> routes = routeRepository.findAll();
        if (routes.isEmpty()) {
            throw new EmptyTableException("No routes in database");
        }
        return routes;
    }
}
