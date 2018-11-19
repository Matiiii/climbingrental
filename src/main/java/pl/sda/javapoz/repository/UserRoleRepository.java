package pl.sda.javapoz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.sda.javapoz.model.UserRole;

/**
 * Created by pablo on 23.03.17.
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);
}
