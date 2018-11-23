package pl.sda.javapoz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.javapoz.model.ProductOrder;

import java.util.List;

@Repository
public interface ProductOrderRepository extends CrudRepository<ProductOrder, Long> {

    List<ProductOrder> findByUserIdId(Long id);

    List<ProductOrder> findByProductIdId(Long id);

}
