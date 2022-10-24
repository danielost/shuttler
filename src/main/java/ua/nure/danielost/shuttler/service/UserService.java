package ua.nure.danielost.shuttler.service;

import ua.nure.danielost.shuttler.exception.EmailTakenException;
import ua.nure.danielost.shuttler.exception.EmptyTableException;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.User;

import java.util.List;

public interface UserService {
    public List<User> getAllUsers() throws EmptyTableException;
    public User getUserById(long id) throws NoSuchUserException;
    public User saveUser(User user) throws EmailTakenException;
}
