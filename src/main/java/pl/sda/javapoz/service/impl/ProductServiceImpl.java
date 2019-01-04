package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.sda.javapoz.model.CountProducts;
import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.ProductEntity;
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
    public ProductEntity findProductById(Long id) {
        return productRepository.findOne(id);
    }

    @Override
    public List<ProductEntity> findAllProducts() {
        List<ProductEntity> products = new LinkedList<>();
        productRepository.findAll().forEach(products::add);
        return products;
    }

    @Override
    public Set<ProductEntity> findProductByName() {
        return new HashSet<>(findAllProducts());
    }

    @Override
    public Set<ProductEntity> findAllProductsByProductNameOrTags(String productNameOrTag) {
        return productRepository.findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(productNameOrTag, productNameOrTag);
    }

    @Override
    public Integer countProductsByName(String name) {
        return productRepository.findByProductName(name).size();
    }

    @Override
    public Integer countProductsByNameAndTime(String name, Date start, Date end) {
        int cnt = 0;
        List<ProductEntity> list = productRepository.findByProductName(name);
        for (ProductEntity product : list) {
            if (productOrderService.isProductAvailableToOrder(product.getId(), start, end)) {
                cnt++;
            }
        }
        return cnt;
    }

    @Override
    public List<CountProducts> countAllProductsByName() {
        Set<ProductEntity> set = findProductByName();
        return addProductsToList(set);
    }

    @Override
    public List<CountProducts> countAllProductsByNameFiltered(String name) {
        Set<ProductEntity> set = findAllProductsByProductNameOrTags(name);
        return addProductsToList(set);
    }

    private List<CountProducts> addProductsToList(Set<ProductEntity> set) {
        List<CountProducts> list = new LinkedList<>();
        set.forEach(product -> list.add(new CountProducts(product, countProductsByName(product.getProductName()))));
        return list;
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByName(Date start, Date end) {
        Set<ProductEntity> set = findProductByName();
        return addProductsWithTimeToList(set, start, end);
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByNameFiltered(Date start, Date end, String name) {
        Set<ProductEntity> set = findAllProductsByProductNameOrTags(name);
        return addProductsWithTimeToList(set, start, end);
    }

    private List<CountProducts> addProductsWithTimeToList(Set<ProductEntity> set, Date start, Date end) {
        List<CountProducts> list = new LinkedList<>();
        set.forEach(product -> list.add(new CountProducts(product, countProductsByNameAndTime(product.getProductName(), start, end))));
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
    public void addProductByAdmin(String productName, Double price, String description, String smallImage, String bigImage, String tags) {
        productRepository.save(new ProductEntity(productName, price, description, smallImage, bigImage, tags));
    }

    @Override
    public void removeProduct(Long id) {
        productRepository.delete(id);
    }

}
