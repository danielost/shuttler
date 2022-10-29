package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.InvalidIdException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchStopException;
import ua.nure.danielost.shuttler.exception.StopAlreadyExistsException;
import ua.nure.danielost.shuttler.model.Stop;

import java.util.List;

public interface StopService {
    public List<Stop> getAll();

    public void addStop(Stop stop) throws StopAlreadyExistsException;

    public void updateStop(Stop stop, long id) throws NoSuchStopException;

    public void addStop(Stop stop, long routeId) throws StopAlreadyExistsException, NoSuchRouteException;

    public void saveStopToRoute(long stopId, long routeId) throws InvalidIdException, StopAlreadyExistsException;

    public void deleteStop(long id) throws NoSuchStopException;

    public void removeStopFromRoute(long stopId, long routeId) throws InvalidIdException, NoSuchStopException;
}
