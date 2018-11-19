package pl.sda.javapoz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import pl.sda.javapoz.model.CountProducts;
import pl.sda.javapoz.model.Link;
import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.repository.ProductRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by RENT on 2017-03-22.
 */
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private UserService userService;

    public Product findProductById(Long id){

        return productRepository.findOne(id);
    }


    public List<Product> fetchProducts(){
        List<Product> products= new ArrayList<Product>();
        Iterable <Product> iterable = productRepository.findAll();
        iterable.forEach(products::add);

        return products;
    }


    public List<Product> findAllProducts(){

        List<Product> products = new ArrayList<>();
        Iterable<Product> iterable = productRepository.findAll();
        iterable.forEach(products :: add);

        return products;
    }

    public Set<Product> findAllUniqueProducts(){

        Set<Product> products = new HashSet<>();
        Iterable<Product> iterable = productRepository.findAll();
        iterable.forEach(products :: add);

        return products;
    }

    public Set<Link> findAllTags(){

        List<Product> products = findAllProducts();
        Set<Link> collect = products.stream()
                .map(e -> e.getTags())
                .map(e -> new Link(StringUtils.capitalize(e), "/shop?category=" + e))
                .collect(Collectors.toSet());

        return collect;
    }

    public Set<Link> findProductByTags(String tag){

        List<Product> products = findAllProducts();
        Set<Link> collect =
                products.stream()
                .filter(e -> {
                    List<String> tags = Arrays.asList(e.getTags().split(";"));
                    return tags.contains(tag);
                })
                .map(e -> new Link(StringUtils.capitalize(e.getProductName()), "/shop?category=" + e))
                .collect(Collectors.toSet());

        return collect;
    }

    public Set<Product> findProductByName(){

        List<Product> list = findAllProducts();
        Set<Product> products = new HashSet<>(list);

        return products;
    }

    public List<Product> findAllProductByName(Product prod){
        return productRepository.findByProductName(prod.getProductName());

    }
    public List<Product> findAllProductByTags(Product prod){
        return productRepository.findByTags(prod.getTags());
    }

    public List<Product> findAllProductByProductNamesAndTags(Product prod){
        return productRepository.findByProductNameAndTags(prod.getProductName(),prod.getTags());
    }

    public Set<Product> findAllProductsByProductNameOrTags(String productNameOrTag){
        return productRepository.findByProductNameIgnoreCaseContainingOrTagsIgnoreCaseContaining(productNameOrTag,productNameOrTag);
    }

    public Integer countProductsByName(String name){

        List<Product> list = productRepository.findByProductName(name);
        return list.size();
    }
    public Integer countProductsByNameAndTime(String name, Date start, Date end ){
        int cnt = 0;
        List<Product> list = productRepository.findByProductName(name);
        for (Product product: list) {
            if(productOrderService.isProductAvailableToOrder(product.getId(),start,end)){
                //list.remove(product);
                cnt++;
            }

        }
        //return list.size();
        return cnt;
    }

    public List<CountProducts> contAllProdactsByName(){
        Set<Product> set = findProductByName();
        List<CountProducts> list = new ArrayList<>();
        for (Product product: set) {
            String name = product.getProductName();
            Integer count = countProductsByName(name);
            list.add(new CountProducts(product,count));
        }
        return list;
    }

    public List<CountProducts> contAllProdactsByNameFitered(String name){
        Set<Product> set = findAllProductsByProductNameOrTags(name);
        List<CountProducts> list = new ArrayList<>();
        for (Product product: set) {
            String nameUnique = product.getProductName();
            Integer count = countProductsByName(nameUnique);
            list.add(new CountProducts(product,count));
        }
        return list;
    }

    public List<CountProducts> contAllAvailableProdactsByName(Date start, Date end){
        Set<Product> set = findProductByName();
        List<CountProducts> list = new ArrayList<>();
        for (Product product: set) {
            String name = product.getProductName();
            Integer count = countProductsByNameAndTime(name, start, end);
            list.add(new CountProducts(product,count));
        }
        return list;
    }

    public List<CountProducts> contAllAvailableProdactsByNameFiltered(Date start, Date end, String name){
        Set<Product> set = findAllProductsByProductNameOrTags(name);
        List<CountProducts> list = new ArrayList<>();
        for (Product product: set) {
            String nameUnique = product.getProductName();
            Integer count = countProductsByNameAndTime(nameUnique, start, end);
            list.add(new CountProducts(product,count));
        }
        return list;
    }


    public Set<Link> findRelatedProducts(Product product) {
        List<String> tagsInProduct = Arrays.asList(product.getTags().split(";"));

        List<Product> products = findAllProducts();

        return products.stream().filter(e -> CollectionUtils.containsAny(Arrays.asList(e.getTags().split(";")),tagsInProduct))
                .map(e -> new Link(StringUtils.capitalize(e.getProductName()), "/product/" + e.getId()))
                .collect(Collectors.toSet());
    }

    public void addProductByAdmin(String productName, Double price, String description, String smallImage, String bigImage, String tags){

        productRepository.save(new Product(productName, price, description, smallImage, bigImage, tags));

    }


    public void removeProduct(Long id){

        productRepository.delete(id);
    }

}
