package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.Link;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductOrderService productOrderService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductOrderService productOrderService) {
        this.productRepository = productRepository;
        this.productOrderService = productOrderService;
    }

    @Override
    public ProductEntity findProductById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public List<ProductEntity> findAllProducts() {
        List<ProductEntity> products = new LinkedList<>();
        productRepository.findAll().forEach(products::add);

        return products;
    }

/*    @Override
    public Set<ProductEntity> findProductByName() {
        return new HashSet<>(findAllProducts());
    }*/

/*    @Override
    public Set<ProductEntity> findAllProductsByProductNameOrTags(String productNameOrTag) {
        return productRepository.findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(productNameOrTag, productNameOrTag);
    }*/

    @Override
    public List<ProductEntity> findAllProductsByProductNameOrTags(String productNameOrTag) {
        return productRepository.findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(productNameOrTag, productNameOrTag);
    }

    @Override
    public Integer countProductsByName(String name) {
        return productRepository.findByProductName(name).getQuantity();
    }

    @Override
    public Integer countProductsAvailableByNameAndTime(String name, Date start, Date end) {
        ProductEntity product = productRepository.findByProductName(name);
        int countOrders = productOrderService.countOrdersProductInPeriod(product.getId(), start, end);
        int quantity = product.getQuantity();

        return quantity-countOrders;
    }

    @Override
    public List<CountProducts> countAllProductsByName() {
        //Set<ProductEntity> set = findProductByName();
        //return addProductsToList(set);
        return addProductsToList(findAllProducts());
    }

/*    @Override
    public List<CountProducts> countAllProductsByNameFiltered(String name) {
        Set<ProductEntity> set = findAllProductsByProductNameOrTags(name);
        return addProductsToList(set);
    }*/

    @Override
    public List<CountProducts> countAllProductsByNameFiltered(String name) {
        List<ProductEntity> list = findAllProductsByProductNameOrTags(name);
        return addProductsToList(list);
    }

/*    private List<CountProducts> addProductsToList(Set<ProductEntity> set) {
        List<CountProducts> list = new LinkedList<>();
        set.forEach(product -> list.add(new CountProducts(product, countProductsByName(product.getProductName()))));
        return list;
    }*/

    private List<CountProducts> addProductsToList(List<ProductEntity> lis) {
        List<CountProducts> list = new LinkedList<>();
        lis.forEach(product -> list.add(new CountProducts(product, countProductsByName(product.getProductName()))));
        return list;
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByName(String dateFilter) {
        String dates[] = dateFilter.split("-");
        Date start = new Date(dates[0]);
        Date end = new Date(dates[1]);
        //Set<ProductEntity> set = findProductByName();
        List<ProductEntity> list = findAllProducts();
        //return addProductsWithTimeToList(set, start, end);
        return addProductsWithTimeToList(list, start, end);
    }

/*    @Override
    public List<CountProducts> countAllAvailableProductsByNameFiltered(String dateFilter, String name) {
        String dates[] = dateFilter.split("-");
        Date start = new Date(dates[0]);
        Date end = new Date(dates[1]);
        Set<ProductEntity> set = findAllProductsByProductNameOrTags(name);
        return addProductsWithTimeToList(set, start, end);
    } */

    @Override
    public List<CountProducts> countAllAvailableProductsByNameFiltered(String dateFilter, String name) {
        String dates[] = dateFilter.split("-");
        Date start = new Date(dates[0]);
        Date end = new Date(dates[1]);
        List<ProductEntity> lis = findAllProductsByProductNameOrTags(name);
        return addProductsWithTimeToList(lis, start, end);
    }

 /*   private List<CountProducts> addProductsWithTimeToList(Set<ProductEntity> set, Date start, Date end) {
        List<CountProducts> list = new LinkedList<>();
        set.forEach(product -> list.add(new CountProducts(product, countProductsAvailableByNameAndTime(product.getProductName(), start, end))));
        return list;
    }*/

    private List<CountProducts> addProductsWithTimeToList(List<ProductEntity> lista, Date start, Date end) {
        List<CountProducts> list = new LinkedList<>();
        lista.forEach(product -> list.add(new CountProducts(product, countProductsAvailableByNameAndTime(product.getProductName(), start, end))));
        return list;
    }

    @Override
    public Set<Link> findRelatedProducts(ProductEntity product) {
        List<String> tagsInProduct = Arrays.asList(product.getTags().split(";"));

        List<ProductEntity> products = findAllProducts();

        return products.stream().filter(e -> CollectionUtils.containsAny(Arrays.asList(e.getTags().split(";")), tagsInProduct))
                .map(e -> new Link(StringUtils.capitalize(e.getProductName()), "/product/" + e.getId()))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Link::getName))));
    }

    @Override
    public void addProductByAdmin(ProductEntity product) {
        productRepository.save(product);
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.delete(id);
    }

    @Override
    public List<CountProducts> countProductsInProductList(List<ProductEntity> productList) {
        Set<ProductEntity> setProducts = new HashSet<>(productList);
        List<CountProducts> countProducts = new LinkedList<>();

        for (ProductEntity product : setProducts) {
            countProducts.add(new CountProducts(product, Collections.frequency(productList, product)));
        }
        return countProducts;
    }

}
