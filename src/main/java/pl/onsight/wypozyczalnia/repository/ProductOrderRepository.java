package pl.onsight.wypozyczalnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;

import java.util.List;

@Repository
public interface ProductOrderRepository extends CrudRepository<ProductOrderEntity, Long> {
  List<ProductOrderEntity> findAllByProductsContaining(ProductEntity product);

  List<ProductOrderEntity> findAllByUserId(Long id);

  List<ProductOrderEntity> findAll();
}
