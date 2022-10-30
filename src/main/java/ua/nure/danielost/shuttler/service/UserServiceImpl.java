package ua.nure.danielost.shuttler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.UsernameTakenException;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchRouteException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.repository.UserRepository;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteService routeService;

    @Override
    public void deleteUserById(long id) throws NoSuchUserException {
        if (!userRepository.findById(id).isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void updateUser(long id, User user) throws NoSuchUserException {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }

        User userToUpdate = foundUser.get();
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setFirstName(user.getLastName());

        userRepository.save(userToUpdate);
    }

    @Override
    public void saveRoute(long userId, long routeId) throws NoSuchRouteException, NoSuchUserException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("No users with " + userId + " id in database");
        }
        User user = userOptional.get();
        Route route = routeService.getRouteById(routeId);

        user.saveRoute(route);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() throws EmptyTableException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EmptyTableException("No users in database");
        }
        return users;
    }

    @Override
    public User getUserById(long id) throws NoSuchUserException {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }

        return foundUser.get();
    }

    @Override
    public User getUserByUsername(String username) throws NoSuchUserException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException("No users with " + username + " email found");
        }

        return user;
    }

    @Override
    public void saveUser(User user) throws UsernameTakenException, NoSuchAlgorithmException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameTakenException("Username is already taken");
        }
        //TODO encode with BCrypt

        userRepository.save(user);
    }

    @Override
    public void deleteRoute(long userId, long routeId) throws NoSuchRouteException, NoSuchUserException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("No users with " + userId + " id in database");
        }
        User user = userOptional.get();
        Route route = routeService.getRouteById(routeId);

        user.deleteRoute(route);
        userRepository.save(user);
    }
}
