package ua.nure.danielost.shuttler.security.jwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.danielost.shuttler.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String name);
}
