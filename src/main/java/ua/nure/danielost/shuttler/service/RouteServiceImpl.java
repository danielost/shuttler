package ua.nure.danielost.shuttler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.repository.RouteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public long updateRouteNumber(long id, Route route) throws NoSuchRouteException {
        Optional<Route> foundRoute = routeRepository.findById(id);
        if (!foundRoute.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }

        Route routeToUpdate = foundRoute.get();
        routeToUpdate.setNumber(route.getNumber());

        routeRepository.save(routeToUpdate);
        return id;
    }

    @Override
    public Route saveRoute(Route route) {
        return routeRepository.save(route);
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
    public long deleteRoute(long id) throws NoSuchRouteException {
        Optional<Route> foundRoute = routeRepository.findById(id);
        if (!foundRoute.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }

        routeRepository.deleteById(id);
        return id;
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
