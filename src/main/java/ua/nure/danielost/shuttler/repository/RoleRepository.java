package ua.nure.danielost.shuttler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.danielost.shuttler.model.Role;
import ua.nure.danielost.shuttler.model.UserRole;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String name);
}
