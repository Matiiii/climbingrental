package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.sda.javapoz.model.CountProducts;
import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.repository.ProductRepository;
import pl.sda.javapoz.service.ProductOrderService;
import pl.sda.javapoz.service.ProductService;

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
    public Product findProductById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public List<Product> findAllProducts() {
        List<Product> products = new LinkedList<>();
        productRepository.findAll().forEach(products::add);

        return products;
    }

    @Override
    public Set<Product> findProductByName() {
        return new HashSet<>(findAllProducts());
    }

    @Override
    public Set<Product> findAllProductsByProductNameOrTags(String productNameOrTag) {
        return productRepository.findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(productNameOrTag, productNameOrTag);
    }

    @Override
    public Integer countProductsByName(String name) {
        return productRepository.findByProductName(name).size();
    }

    @Override
    public Integer countProductsByNameAndTime(String name, Date start, Date end) {
        int cnt = 0;
        List<Product> list = productRepository.findByProductName(name);
        for (Product product : list) {
            if (productOrderService.isProductAvailableToOrder(product.getId(), start, end)) {
                cnt++;
            }
        }
        return cnt;
    }

    @Override
    public List<CountProducts> countAllProductsByName() {
        Set<Product> set = findProductByName();
        return addProductsToList(set);
    }

    @Override
    public List<CountProducts> countAllProductsByNameFiltered(String name) {
        Set<Product> set = findAllProductsByProductNameOrTags(name);
        return addProductsToList(set);
    }

    private List<CountProducts> addProductsToList(Set<Product> set) {
        List<CountProducts> list = new LinkedList<>();
        set.forEach(product -> list.add(new CountProducts(product, countProductsByName(product.getProductName()))));
        return list;
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByName(Date start, Date end) {
        Set<Product> set = findProductByName();
        return addProductsWithTimeToList(set, start, end);
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByNameFiltered(Date start, Date end, String name) {
        Set<Product> set = findAllProductsByProductNameOrTags(name);
        return addProductsWithTimeToList(set, start, end);
    }

    private List<CountProducts> addProductsWithTimeToList(Set<Product> set, Date start, Date end) {
        List<CountProducts> list = new LinkedList<>();
        set.forEach(product -> list.add(new CountProducts(product, countProductsByNameAndTime(product.getProductName(), start, end))));
        return list;
    }

    @Override
    public Set<Link> findRelatedProducts(Product product) {
        List<String> tagsInProduct = Arrays.asList(product.getTags().split(";"));

        List<Product> products = findAllProducts();

        return products.stream().filter(e -> CollectionUtils.containsAny(Arrays.asList(e.getTags().split(";")), tagsInProduct))
                .map(e -> new Link(StringUtils.capitalize(e.getProductName()), "/product/" + e.getId()))
                .collect(Collectors.toSet());
    }

    @Override
    public void addProductByAdmin(String productName, Double price, String description, String smallImage, String bigImage, String tags) {
        productRepository.save(new Product(productName, price, description, smallImage, bigImage, tags));
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.delete(id);
    }

}
