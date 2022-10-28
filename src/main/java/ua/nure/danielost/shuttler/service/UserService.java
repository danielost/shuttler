package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.EmailTakenException;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.User;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService {
    public List<User> getAllUsers() throws EmptyTableException;
    public User getUserById(long id) throws NoSuchUserException;
    public User getUserByEmail(String email) throws NoSuchUserException;
    public void saveUser(User user) throws EmailTakenException, NoSuchAlgorithmException;
    public void deleteUserById(long id) throws NoSuchUserException;
    public void updateUser(long id, User user) throws NoSuchUserException;
    public void saveRoute(long userId, long routeId) throws NoSuchRouteException, NoSuchUserException;
    public void deleteRoute(long userId, long routeId) throws NoSuchRouteException, NoSuchUserException;

}
