package pl.sda.javapoz.service;

import pl.sda.javapoz.model.CountProducts;
import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.Product;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ProductService {
    Product findProductById(Long id);

    List<Product> findAllProducts();

    Set<Product> findProductByName();

    Set<Product> findAllProductsByProductNameOrTags(String productNameOrTag);

    Integer countProductsByName(String name);

    Integer countProductsByNameAndTime(String name, Date start, Date end);

    List<CountProducts> countAllProductsByName();

    List<CountProducts> countAllProductsByNameFiltered(String name);

    List<CountProducts> countAllAvailableProductsByName(Date start, Date end);

    List<CountProducts> countAllAvailableProductsByNameFiltered(Date start, Date end, String name);

    Set<Link> findRelatedProducts(Product product);

    void addProductByAdmin(String productName, Double price, String description, String smallImage, String bigImage, String tags);

    void removeProduct(Long id);
}
