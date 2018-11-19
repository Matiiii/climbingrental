package pl.sda.javapoz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.javapoz.model.ProductOrder;
import pl.sda.javapoz.model.User;

import java.util.List;

/**
 * Created by pablo on 22.03.17.
 */

public interface ProductOrderRepository extends CrudRepository<ProductOrder, Long> {

    List<ProductOrder> findByUserIdId(Long id);
    List<ProductOrder> findByProductIdId(Long id);

}
