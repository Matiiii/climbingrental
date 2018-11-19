package pl.sda.javapoz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.javapoz.model.User;

/**
 * Created by pablo on 22.03.17.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByFirstNameAndLastName(String firstName, String lastName);


}
