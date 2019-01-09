package pl.sda.javapoz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.javapoz.model.entity.ProductOrderEntity;

import java.util.List;

@Repository
public interface ProductOrderRepository extends CrudRepository<ProductOrderEntity, Long> {
    List<ProductOrderEntity> findByUserId(Long id);
}
