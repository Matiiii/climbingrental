package pl.sda.javapoz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.javapoz.model.ProductEntity;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<ProductEntity, Long> {

    List<ProductEntity> findByProductName(String productName);

    List<ProductEntity> findByTags(String tags);

    List<ProductEntity> findByProductNameAndTags(String productName, String Tags);

    Set<ProductEntity> findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(String productName, String Tags);

}
