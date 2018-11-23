package pl.sda.javapoz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.sda.javapoz.model.Product;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findByProductName(String productName);

    List<Product> findByTags(String tags);

    List<Product> findByProductNameAndTags(String productName, String Tags);

    Set<Product> findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(String productName, String Tags);

}
