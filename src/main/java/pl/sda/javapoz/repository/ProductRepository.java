package pl.sda.javapoz.repository;

import org.springframework.data.repository.CrudRepository;
import pl.sda.javapoz.model.Product;

import java.util.List;
import java.util.Set;

/**
 * Created by RENT on 2017-03-22.
 */

public interface ProductRepository extends CrudRepository<Product, Long>{

    List<Product> findByProductName(String productName);
    List<Product> findByTags(String tags);
    List<Product> findByProductNameAndTags(String productName, String Tags);
    Set<Product> findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(String productName, String Tags);

}
