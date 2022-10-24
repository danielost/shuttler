package ua.nure.danielost.shuttler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.EmailTakenException;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.repository.UserRepository;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

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
