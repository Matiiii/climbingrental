package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.Link;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collector;
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
    public Integer countProductsAvailableByNameAndTime(String name, Date start, Date end) {
        ProductEntity product = productRepository.findByProductName(name);
        int productCountInOrders = productOrderService.countOrdersProductInPeriod(product.getId(), start, end);
        int productQuantity = product.getQuantity();

        return productQuantity - productCountInOrders;
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

    private List<CountProducts> createListOfCountProduct(List<ProductEntity> products) {
        return products.stream().map(product -> new CountProducts(product, product.getQuantity())).collect(Collectors.toList());
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByName(String date) throws ParseException {
        Date[] dates = DateFilter.changeStringToDate(date);
        List<ProductEntity> list = findAllProducts();

        return addProductsWithTimeToList(list, dates[0], dates[1]);
    }

    @Override
    public List<CountProducts> countAllAvailableProductsByNameFiltered(String date, String name) throws ParseException {
        Date[] dates = DateFilter.changeStringToDate(date);
        List<ProductEntity> products = findAllProductsByProductNameOrTags(name);

        return addProductsWithTimeToList(products, dates[0], dates[1]);
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
    public List<CountProducts> countProductsInProductList(List<ProductEntity> productList) {
        Set<ProductEntity> setProducts = new HashSet<>(productList);
        List<CountProducts> countProducts = new LinkedList<>();

        for (ProductEntity product : setProducts) {
            countProducts.add(new CountProducts(product, Collections.frequency(productList, product)));
        }

        return countProducts;
    }

    @Override
    public void editProduct(ProductEntity product) {
        ProductEntity editedProduct=new ProductEntity();
        editedProduct.setProductName(product.getProductName());
        editedProduct.setPrice(product.getPrice());
        editedProduct.setBigImage(product.getBigImage());
        editedProduct.setSmallImage(product.getSmallImage());
        editedProduct.setDescription(product.getDescription());
        editedProduct.setTags(product.getTags());
        editedProduct.setQuantity(product.getQuantity());
    }
}

