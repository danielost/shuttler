package ua.nure.danielost.shuttler.security.jwt;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import ua.nure.danielost.shuttler.model.Role;
import ua.nure.danielost.shuttler.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    public JwtUserFactory() {
    }

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPassword(),
                mapToGrantedAuthorities(new HashSet<>(user.getRoles()))
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles) {
        return roles.stream()
                .map(role ->
                        new SimpleGrantedAuthority(role.getName().toString()))
                .collect(Collectors.toList());
    }
}