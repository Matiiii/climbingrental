package pl.sda.javapoz.service;

import pl.sda.javapoz.model.CountProducts;
import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.entity.ProductEntity;
import pl.sda.javapoz.model.entity.ProductOrderEntity;

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

    void addProductByAdmin(String productName, Double price, String description, String smallImage, String bigImage, String tags, Integer quantity);

    void removeProduct(Long id);

    boolean isOrderAvailableToSave(ProductOrderEntity order);

    List<CountProducts> countProductsInProductList(List<ProductEntity> productList);
}
