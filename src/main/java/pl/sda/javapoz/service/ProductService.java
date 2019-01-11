package pl.sda.javapoz.service;

import pl.sda.javapoz.model.CountProducts;
import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.entity.ProductEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductEntity findProductById(Long id);

    List<ProductEntity> findAllProducts();

    Set<ProductEntity> findProductByName();

    Set<ProductEntity> findAllProductsByProductNameOrTags(String productNameOrTag);

    Integer countProductsByName(String name);

    Integer countProductsByNameAndTime(String name, Date start, Date end);

    List<CountProducts> countAllProductsByName();

    List<CountProducts> countAllProductsByNameFiltered(String name);

    List<CountProducts> countAllAvailableProductsByName(String dateFilter);

    List<CountProducts> countAllAvailableProductsByNameFiltered(String dateFiltered, String name);

    Set<Link> findRelatedProducts(ProductEntity product);

    void addProductByAdmin(String productName, Double price, String description, String smallImage, String bigImage, String tags);

    void removeProduct(Long id);

    ProductEntity addProduct(ProductEntity newProduct);
}
