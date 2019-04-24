package pl.onsight.wypozyczalnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.onsight.wypozyczalnia.model.entity.ConfirmationToken;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, String> {

  ConfirmationToken findByConfirmationToken(String confirmationToken);
}
