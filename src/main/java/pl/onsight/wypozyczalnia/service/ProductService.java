package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.Link;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductEntity findProductById(Long id);

    List<ProductEntity> findAllProducts();

    Integer countProductsAvailableByNameAndTime(String name, Date start, Date end);

    List<CountProducts> countAllProductsByName();

    List<CountProducts> countAllProductsByNameFiltered(String name);

    List<CountProducts> createListOfCountProduct(List<ProductEntity> products);

    List<CountProducts> countAllAvailableProductsByName(String dateFilter) throws ParseException;

    List<CountProducts> countAllAvailableProductsByNameFiltered(String dateFiltered, String name) throws ParseException;

    Set<Link> findRelatedProducts(ProductEntity product);

    void removeProduct(Long id);

    void addProduct(ProductEntity product);

    List<CountProducts> changeProductEntityListToCountProductsList(List<ProductEntity> productList);

}
