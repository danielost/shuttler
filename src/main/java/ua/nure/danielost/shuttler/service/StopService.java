package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.InvalidIdException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchStopException;
import ua.nure.danielost.shuttler.exception.StopAlreadyExistsException;
import ua.nure.danielost.shuttler.model.Stop;

import java.util.List;

public interface StopService {

    List<Stop> getAll();

    void addStop(Stop stop) throws StopAlreadyExistsException;

    void updateStop(Stop stop, long id) throws NoSuchStopException;

    void addStop(Stop stop, long routeId) throws StopAlreadyExistsException, NoSuchRouteException;

    void saveStopToRoute(long stopId, long routeId) throws InvalidIdException, StopAlreadyExistsException;

    void deleteStop(long id) throws NoSuchStopException;

    void removeStopFromRoute(long stopId, long routeId) throws InvalidIdException, NoSuchStopException;
}
