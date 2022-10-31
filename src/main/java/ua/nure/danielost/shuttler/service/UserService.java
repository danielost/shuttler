package ua.nure.danielost.shuttler.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.nure.danielost.shuttler.exception.UsernameTakenException;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.User;

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
}
