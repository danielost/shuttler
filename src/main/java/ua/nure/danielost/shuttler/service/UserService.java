package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.*;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.User;

import javax.management.relation.RoleNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAll() throws EmptyTableException;

    User findById(long id) throws NoSuchUserException;

    User findByUsername(String username) throws NoSuchUserException;

    User register(User user) throws UsernameTakenException, NoSuchAlgorithmException;

    void delete(long id) throws NoSuchUserException, InvalidIdException;

    void update(long id, User user) throws NoSuchUserException, InvalidIdException;

    void saveRoute(long userId, long routeId) throws NoSuchRouteException, NoSuchUserException, InvalidIdException;

    void deleteRoute(long userId, long routeId) throws NoSuchRouteException, NoSuchUserException, InvalidIdException;

    void setRole(long roleId, long userId) throws InvalidIdException;

    void unsetRole(long roleId, long userId) throws InvalidIdException, RoleNotFoundException;

    List<User> getAllUsersByType(String type);

    Set<Route> getSavedRoutes(long id) throws InvalidIdException, NoSuchUserException;

    void subscribe(long id) throws InvalidIdException;
}