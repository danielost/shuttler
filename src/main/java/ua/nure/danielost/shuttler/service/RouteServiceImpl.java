package ua.nure.danielost.shuttler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.InvalidIdException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.RouteAlreadyExistsException;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Stop;
import ua.nure.danielost.shuttler.model.Vehicle;
import ua.nure.danielost.shuttler.model.VehicleType;
import ua.nure.danielost.shuttler.repository.RouteRepository;
import ua.nure.danielost.shuttler.repository.StopRepository;
import ua.nure.danielost.shuttler.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StopRepository stopRepository;

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
    public List<Route> getRoutes(VehicleType type) {
        return routeRepository.findByType(type);
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
    public List<Route> getOptimalRoutes(
            long routeId,
            long departId,
            long destinationId
    ) throws InvalidIdException {
        if (departId == destinationId) {
            throw new InvalidIdException("Stops can't be the same");
        }

        Optional<Route> routeOptional = routeRepository.findById(routeId);
        Optional<Stop> departStopOptional = stopRepository.findById(departId);
        Optional<Stop> destinationStopOptional = stopRepository.findById(destinationId);

        if (!routeOptional.isPresent() || !departStopOptional.isPresent() || !destinationStopOptional.isPresent()) {
            throw new InvalidIdException("No routes or stops with such IDs");
        }

        Route route = routeOptional.get();
        Stop departStop = departStopOptional.get();
        Stop destinationStop = destinationStopOptional.get();
        List<Route> optimalRoutes = new ArrayList<>();
        List<Route> existingRoutes = routeRepository.findAll();

        double congestion = route.getCongestion();

        for (Route currentRoute: existingRoutes) {
            if (routeContainsStops(currentRoute, departStop, destinationStop) &&
                            currentRoute.getCongestion() < congestion &&
                            currentRoute.getVehicles().size() > 0)
            {
                optimalRoutes.add(currentRoute);
            }
        }

        return optimalRoutes;
    }

    private boolean routeContainsStops(Route route, Stop stopA, Stop stopB) {
        Set<Stop> stops = route.getStops();
        return stops.contains(stopA) && stops.contains(stopB);
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
