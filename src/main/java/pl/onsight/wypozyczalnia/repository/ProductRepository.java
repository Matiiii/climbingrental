package pl.onsight.wypozyczalnia.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {
    ProductEntity findByProductName(String productName);

    List<ProductEntity> findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(String productName, String Tags);
}
