package ua.nure.danielost.shuttler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.EmailTakenException;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public long deleteUserById(long id) throws NoSuchUserException {
        if (!userRepository.findById(id).isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }
        userRepository.deleteById(id);
        return id;
    }

    @Override
    public long updateUser(long id, User user) throws NoSuchUserException {
        Optional<User> foundUser = userRepository.findById(id);
        if (!foundUser.isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }

        User userToUpdate = foundUser.get();
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setEmail(user.getEmail());
        userToUpdate.setName(user.getName());
        userToUpdate.setSurname(user.getSurname());

        userRepository.save(userToUpdate);
        return id;
    }

    @Override
    public List<User> getAllUsers() throws EmptyTableException {
        if (userRepository.findAll().isEmpty()) {
            throw new EmptyTableException("No users in database");
        }
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) throws NoSuchUserException {
        if (!userRepository.findById(id).isPresent()) {
            throw new NoSuchUserException("No users with " + id + " id in database");
        }

        return userRepository.findById(id).get();
    }

    @Override
    public User saveUser(User user) throws EmailTakenException {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new EmailTakenException("Email is already taken");
        }

        return userRepository.save(user);
    }
}
