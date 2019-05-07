package pl.onsight.wypozyczalnia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.onsight.wypozyczalnia.model.entity.UserRoleEntity;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}
