package pl.onsight.wypozyczalnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);

    UserEntity findByEmailIgnoreCase(String email);
}
