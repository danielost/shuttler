package ua.nure.danielost.shuttler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.InvalidIdException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchStopException;
import ua.nure.danielost.shuttler.exception.StopAlreadyExistsException;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.Stop;
import ua.nure.danielost.shuttler.repository.RouteRepository;
import ua.nure.danielost.shuttler.repository.StopRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StopServiceImpl implements StopService {

    @Autowired
    private StopRepository stopRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Override
    public List<Stop> getAll() {
        return stopRepository.findAll();
    }

    @Override
    public void addStop(Stop stop) throws StopAlreadyExistsException {
        Stop stopExisting = stopRepository.findByStreetAndNumber(stop.getStreet(), stop.getNumber());
        if (stopExisting != null) {
            throw new StopAlreadyExistsException("Stop " + stop.getStreet() + ", " + stop.getNumber() + " already exists");
        }
        stopRepository.save(stop);
    }

    @Override
    public void removeStopFromRoute(long stopId, long routeId) throws InvalidIdException, NoSuchStopException {
        Optional<Stop> stopOptional = stopRepository.findById(stopId);
        Optional<Route> routeOptional = routeRepository.findById(routeId);

        if (!stopOptional.isPresent() || !routeOptional.isPresent()) {
            throw new InvalidIdException("Invalid route or stop id");
        }

        Stop stop = stopOptional.get();
        Route route = routeOptional.get();

        if (!route.getStops().contains(stop)) {
            throw new NoSuchStopException("Route doesn't contain such stop");
        }

        route.getStops().remove(stop);
        routeRepository.save(route);
    }

    @Override
    public void deleteStop(long id) throws NoSuchStopException {
        Optional<Stop> stopOptional = stopRepository.findById(id);

        if (!stopOptional.isPresent()) {
            throw new NoSuchStopException("Stop doesn't exist");
        }

        Stop stop = stopOptional.get();
        stop.getRoutes().forEach(route -> route.getStops().remove(stop));

        stopRepository.delete(stopOptional.get());
    }

    @Override
    public void saveStopToRoute(long stopId, long routeId) throws InvalidIdException, StopAlreadyExistsException {
        Optional<Stop> stopOptional = stopRepository.findById(stopId);
        Optional<Route> routeOptional = routeRepository.findById(routeId);

        if (!stopOptional.isPresent() || !routeOptional.isPresent()) {
            throw new InvalidIdException("Invalid route or stop id");
        }

        Stop stop = stopOptional.get();
        Route route = routeOptional.get();

        if (route.getStops().contains(stop)) {
            throw new StopAlreadyExistsException("This route already has this stop");
        }

        route.addStop(stop);
        routeRepository.save(route);
    }

    @Override
    public void addStop(Stop stop, long routeId) throws StopAlreadyExistsException, NoSuchRouteException {
        Stop stopExisting = stopRepository.findByStreetAndNumber(stop.getStreet(), stop.getNumber());
        if (stopExisting != null) {
            throw new StopAlreadyExistsException("Stop " + stop.getStreet() + ", " + stop.getNumber() + " already exists");
        }
        Optional<Route> routeOptional = routeRepository.findById(routeId);
        if (!routeOptional.isPresent()) {
            throw new NoSuchRouteException("No routes with id " + routeId + " exist");
        }
        Route route = routeOptional.get();
        stopRepository.save(stop);
        route.addStop(stop);
        routeRepository.save(route);
    }

    @Override
    public void updateStop(Stop stop, long id) throws NoSuchStopException {
        Optional<Stop> stopOptional = stopRepository.findById(id);
        if (!stopOptional.isPresent()) {
            throw new NoSuchStopException("Stop {id " + id + "} doesn't exist");
        }
        Stop stopToUpdate = stopOptional.get();
        stopToUpdate.setNumber(stop.getNumber());
        stopToUpdate.setStreet(stop.getStreet());
        stopRepository.save(stopToUpdate);
    }
}
