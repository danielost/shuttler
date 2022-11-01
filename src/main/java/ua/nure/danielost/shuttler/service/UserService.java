package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.*;
import ua.nure.danielost.shuttler.model.User;

import javax.management.relation.RoleNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {

    public List<User> getAll() throws EmptyTableException;

    public User findById(long id) throws NoSuchUserException;

    public User findByUsername(String username) throws NoSuchUserException;

    public User register(User user) throws UsernameTakenException, NoSuchAlgorithmException;

    public void delete(long id) throws NoSuchUserException;

    public void update(long id, User user) throws NoSuchUserException;

    public void saveRoute(long userId, long routeId) throws NoSuchRouteException, NoSuchUserException;

    public void deleteRoute(long userId, long routeId) throws NoSuchRouteException, NoSuchUserException;

    public void setRole(long roleId, long userId) throws InvalidIdException;

    public void unsetRole(long roleId, long userId) throws InvalidIdException, RoleNotFoundException;

    public List<User> getAllUsersByType(String type);
}
