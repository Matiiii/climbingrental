package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.Link;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductEntity findProductById(Long id);

    List<ProductEntity> findAllProducts();

    Set<ProductEntity> findProductByName();

    Set<ProductEntity> findAllProductsByProductNameOrTags(String productNameOrTag);

    Integer countProductsByName(String name);

    Integer countProductsAvailableByNameAndTime(String name, Date start, Date end);

    List<CountProducts> countAllProductsByName();

    List<CountProducts> countAllProductsByNameFiltered(String name);

    List<CountProducts> countAllAvailableProductsByName(String dateFilter);

    List<CountProducts> countAllAvailableProductsByNameFiltered(String dateFiltered, String name);

    Set<Link> findRelatedProducts(ProductEntity product);

    void removeProduct(Long id);

    ProductEntity addProduct(ProductEntity newProduct);

    boolean isOrderAvailableToSave(ProductOrderEntity order);

    List<CountProducts> countProductsInProductList(List<ProductEntity> productList);
}
