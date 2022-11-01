package ua.nure.danielost.shuttler.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.danielost.shuttler.exception.NoSuchUserException;
import ua.nure.danielost.shuttler.model.User;
import ua.nure.danielost.shuttler.security.jwt.JwtUserFactory;
import ua.nure.danielost.shuttler.service.UserService;

@Service
public class ShuttlerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.findByUsername(username);
            return JwtUserFactory.create(user);
        } catch (NoSuchUserException e) {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
