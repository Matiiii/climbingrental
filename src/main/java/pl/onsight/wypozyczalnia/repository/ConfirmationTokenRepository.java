package pl.onsight.wypozyczalnia.repository;

import org.springframework.data.repository.CrudRepository;
import pl.onsight.wypozyczalnia.model.entity.ConfirmationToken;

public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationToken, Long> {

  ConfirmationToken findByConfirmationToken(String confirmationToken);
}
