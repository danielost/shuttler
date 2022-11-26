package ua.nure.danielost.shuttler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.configuration.SecurityConfig;
import ua.nure.danielost.shuttler.exception.*;
import ua.nure.danielost.shuttler.model.*;
import ua.nure.danielost.shuttler.repository.RouteRepository;
import ua.nure.danielost.shuttler.repository.StopRepository;
import ua.nure.danielost.shuttler.repository.UserRepository;
import ua.nure.danielost.shuttler.service.RouteService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StopRepository stopRepository;

    @Override
    public List<Route> findByStops(
            long stopA,
            long stopB
    ) throws NoSuchStopException {
        List<Route> routes = routeRepository.findAll();
        List<Route> result = new ArrayList<>();
        Optional<Stop> stopAOptional = stopRepository.findById(stopA);
        Optional<Stop> stopBOptional = stopRepository.findById(stopB);

        if (!stopAOptional.isPresent() || !stopBOptional.isPresent()) {
            throw new NoSuchStopException("Invalid stop id");
        }
        Stop stopAEntity = stopAOptional.get();
        Stop stopBEntity = stopBOptional.get();

        for (Route route: routes) {
            Set<Stop> stops = route.getStops();
            if (stops.contains(stopAEntity) && stops.contains(stopBEntity)) {
                result.add(route);
            }
        }
        result.sort(Comparator.comparing(Route::getCongestion));
        return result;
    }

    @Override
    public void updateRouteNumber(
            long id,
            Route route
    ) throws NoSuchRouteException, InvalidIdException {
        Optional<Route> foundRoute = routeRepository.findById(id);
        if (!foundRoute.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }

        Route routeToUpdate = foundRoute.get();

        String username = SecurityConfig.getUsernameByContext();
        if (!routeToUpdate.getCreator().getUsername().equals(username)) {
            throw new InvalidIdException("You can modify only your own routes");
        }

//        routeValidator(route);

        routeToUpdate.setNumber(route.getNumber());

        routeRepository.save(routeToUpdate);
    }

    @Override
    public List<Route> getRoutes(VehicleType type) {
        return routeRepository.findByType(type);
    }

    @Override
    public void saveRoute(
            Route route,
            long id
    ) throws RouteAlreadyExistsException, NoSuchUserException, InvalidIdException {
        if (routeRepository.findByNumber(route.getNumber()) != null) {
            throw new RouteAlreadyExistsException("Route with number " + route.getNumber() + " already exists");
        }

        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("User doesn't exist");
        }

        String username = SecurityConfig.getUsernameByContext();
        User user = userOptional.get();
        if (!user.getUsername().equals(username)) {
            throw new InvalidIdException("Only you can be the owner of a route created by you");
        }

//        routeValidator(route);

        route.setCreator(userOptional.get());

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
    ) throws InvalidIdException, NoSubscriptionException {
        if (departId == destinationId) {
            throw new InvalidIdException("Stops can't be the same");
        }

        Optional<Route> routeOptional = routeRepository.findById(routeId);
        Optional<Stop> departStopOptional = stopRepository.findById(departId);
        Optional<Stop> destinationStopOptional = stopRepository.findById(destinationId);

        if (!routeOptional.isPresent() || !departStopOptional.isPresent() || !destinationStopOptional.isPresent()) {
            throw new InvalidIdException("No routes or stops with such IDs");
        }

        String username = SecurityConfig.getUsernameByContext();
        User user = userRepository.findByUsername(username);
        if (user.getSubscriptions().isEmpty()) {
            throw new NoSubscriptionException("User {" + username + "} doesn't have a subscription");
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
        optimalRoutes.sort(Comparator.comparing(Route::getCongestion));
        return optimalRoutes;
    }

    @Override
    public List<Route> getRoutesByUserId(long userId) throws InvalidIdException {
        Optional<User> userOptional = userRepository.findById(userId);

        if (!userOptional.isPresent()) {
            throw new InvalidIdException("No users with id {id: " + userId + "} in database");
        }

        String username = SecurityConfig.getUsernameByContext();
        User user = userOptional.get();

        if (!user.getUsername().equals(username)) {
            throw new UsernameNotFoundException("You can see only your own routes");
        }

        return routeRepository.findByCreator(user);
    }

    private boolean routeContainsStops(
            Route route,
            Stop stopA,
            Stop stopB
    ) {
        Set<Stop> stops = route.getStops();
        return stops.contains(stopA) && stops.contains(stopB);
    }

    @Override
    public void deleteRoute(long id) throws NoSuchRouteException, InvalidIdException {
        Optional<Route> foundRoute = routeRepository.findById(id);
        if (!foundRoute.isPresent()) {
            throw new NoSuchRouteException("No routes with " + id + " id in database");
        }
        String username = SecurityConfig.getUsernameByContext();
        Route route = foundRoute.get();

        if (!route.getCreator().getUsername().equals(username)) {
            throw new InvalidIdException("You can delete only your own routes");
        }

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

    private void routeValidator(Route route) {
        String numberRegex = "^[0-9]{1,3}$";
        Pattern numberPattern = Pattern.compile(numberRegex);
        Matcher numberMatcher = numberPattern.matcher(new StringBuilder(route.getNumber()));

        if (!numberMatcher.find()) {
            throw new PatternSyntaxException("Bad route number", numberRegex, 0);
        }
    }
}
