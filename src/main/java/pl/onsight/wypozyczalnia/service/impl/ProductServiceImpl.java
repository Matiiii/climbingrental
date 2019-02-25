package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.Link;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;

import java.text.ParseException;
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
        return productRepository.findAll();
    }

    @Override
    public Integer countProductsAvailableByNameAndTime(String name, Date start, Date end) {
        ProductEntity product = productRepository.findByProductName(name);
        int productCountInOrders = productOrderService.countNumberOfProductInOrdersInPeriod(product, start, end);
        int productQuantity = product.getQuantity();
        int availableProduct = productQuantity - productCountInOrders;

        return availableProduct > 0 ? availableProduct : 0;
    }

    @Override
    public List<CountProducts> countAllProductsByName() {
        return createListOfCountProduct(findAllProducts());
    }

    @Override
    public List<CountProducts> countAllProductsByNameFiltered(String name) {
        return createListOfCountProduct(findAllProductsByProductNameOrTags(name));
    }

    private List<ProductEntity> findAllProductsByProductNameOrTags(String productNameOrTag) {
        return productRepository.findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(productNameOrTag, productNameOrTag);
    }

    @Override
    public List<CountProducts> createListOfCountProduct(List<ProductEntity> products) {
        return products.stream().filter(productEntity -> productEntity.getAvailable()).map(product -> new CountProducts(product, product.getQuantity())).collect(Collectors.toList());
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByName(String date) throws ParseException {
        Date[] dates = DateFilter.changeStringToDate(date);

        return addProductsWithTimeToList(findAllProducts(), dates[0], dates[1]);
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByNameFiltered(String date, String name) throws ParseException {
        Date[] dates = DateFilter.changeStringToDate(date);

        return addProductsWithTimeToList(findAllProductsByProductNameOrTags(name), dates[0], dates[1]);
    }

    private List<CountProducts> addProductsWithTimeToList(List<ProductEntity> products, Date start, Date end) {
        return products.stream().map(product -> new CountProducts(product, countProductsAvailableByNameAndTime(product.getProductName(), start, end))).collect(Collectors.toList());
    }

    @Override
    public Set<Link> findRelatedProducts(ProductEntity product) {
        List<String> tagsInProduct = Arrays.asList(product.getTags().split(","));
        List<ProductEntity> products = findAllProducts();

        return products.stream().filter(p -> CollectionUtils.containsAny(Arrays.asList(p.getTags().split(",")), tagsInProduct))
                .map(e -> new Link(StringUtils.capitalize(e.getProductName()), "/product/" + e.getId()))
                .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Link::getName))));
    }


    @Override
    public void removeProduct(Long id) {
        productRepository.delete(id);
    }

    @Override
    public void addProduct(ProductEntity product) {
        productRepository.save(product);
    }

    @Override
    public List<CountProducts> changeProductEntityListToCountProductsList(List<ProductEntity> productList) {
        Set<ProductEntity> setProducts = new HashSet<>(productList);
        List<CountProducts> countProducts = new LinkedList<>();

        for (ProductEntity product : setProducts) {
            countProducts.add(new CountProducts(product, Collections.frequency(productList, product)));
        }
        return countProducts;
    }




}

