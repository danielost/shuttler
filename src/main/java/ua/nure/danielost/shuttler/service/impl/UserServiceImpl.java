package ua.nure.danielost.shuttler.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.configuration.SecurityConfig;
import ua.nure.danielost.shuttler.exception.*;
import ua.nure.danielost.shuttler.model.Role;
import ua.nure.danielost.shuttler.model.Route;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.repository.RoleRepository;
import ua.nure.danielost.shuttler.repository.UserRepository;
import ua.nure.danielost.shuttler.service.RouteService;
import ua.nure.danielost.shuttler.service.UserService;

import javax.management.relation.RoleNotFoundException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RouteService routeService;

    @Override
    public void delete(long id) throws NoSuchUserException, InvalidIdException {
        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }

        String username = SecurityConfig.getUsernameByContext();
        if (!username.equals(userOptional.get().getUsername())) {
            throw new InvalidIdException("You can't delete other users");
        }

        userRepository.deleteById(id);
    }

    @Override
    public void update(
            long id,
            User user
    ) throws NoSuchUserException, InvalidIdException {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }

        User userToUpdate = userOptional.get();
        String username = SecurityConfig.getUsernameByContext();

        if (!userToUpdate.getUsername().equals(username)) {
            throw new InvalidIdException("You are allowed to update only your personal info");
        }

        userToUpdate.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userToUpdate.setFirstName(user.getFirstName());
        userToUpdate.setLastName(user.getLastName());

        userRepository.save(userToUpdate);
    }

    @Override
    public Set<Route> getSavedRoutes(long id) throws InvalidIdException, NoSuchUserException {
        Optional<User> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }
        User user = userOptional.get();
        String username = SecurityConfig.getUsernameByContext();
        if (!user.getUsername().equals(username)) {
            throw new InvalidIdException("You are allowed to see only your saved routes");
        }

        return user.getSavedRoutes();
    }

    @Override
    public void saveRoute(
            long userId,
            long routeId
    ) throws NoSuchRouteException, NoSuchUserException, InvalidIdException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("No users with " + userId + " id in database");
        }
        User user = userOptional.get();
        String username = SecurityConfig.getUsernameByContext();
        if (!user.getUsername().equals(username)) {
            throw new InvalidIdException("You are allowed to save routes only to your account");
        }

        Route route = routeService.getRouteById(routeId);

        user.saveRoute(route);
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() throws EmptyTableException {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new EmptyTableException("No users in database");
        }
        return users;
    }

    @Override
    public User findById(long id) throws NoSuchUserException {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }

        return foundUser.get();
    }

    @Override
    public User findByUsername(String username) throws NoSuchUserException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new NoSuchUserException("No users with " + username + " email found");
        }

        return user;
    }

    @Override
    public User register(User user) throws UsernameTakenException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new UsernameTakenException("Username is already taken");
        }
        Role role = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void unsetRole(
            long roleId,
            long userId
    ) throws InvalidIdException, RoleNotFoundException {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!roleOptional.isPresent() || !userOptional.isPresent()) {
            throw new InvalidIdException("Invalid user or role ID");
        }

        User user = userOptional.get();
        List<Role> roles = user.getRoles();

        Role role = roleOptional.get();

        if (!roles.contains(role)) {
            throw new RoleNotFoundException("User doesn't have such a role");
        }
        roles.remove(role);
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsersByType(String type) {
        List<User> users = userRepository.findAll();
        List<User> result = new ArrayList<>();
        Role role = roleRepository.findByName(type);

        for (User user: users) {
            if (user.getRoles().contains(role)) {
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public void setRole(
            long roleId,
            long userId
    ) throws InvalidIdException {
        Optional<Role> roleOptional = roleRepository.findById(roleId);
        Optional<User> userOptional = userRepository.findById(userId);

        if (!roleOptional.isPresent() || !userOptional.isPresent()) {
            throw new InvalidIdException("Invalid user or role ID");
        }

        User user = userOptional.get();
        List<Role> roles = user.getRoles();

        Role role = roleOptional.get();

        if (!roles.contains(role)) {
            roles.add(role);
            userRepository.save(user);
        }
    }

    @Override
    public void deleteRoute(
            long userId,
            long routeId
    ) throws NoSuchRouteException, NoSuchUserException, InvalidIdException {
        Optional<User> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            throw new NoSuchUserException("No users with " + userId + " id in database");
        }
        User user = userOptional.get();
        String username = SecurityConfig.getUsernameByContext();
        if (!user.getUsername().equals(username)) {
            throw new InvalidIdException("You are allowed to remove routes only from your account");
        }
        Route route = routeService.getRouteById(routeId);

        user.deleteRoute(route);
        userRepository.save(user);
    }
}
