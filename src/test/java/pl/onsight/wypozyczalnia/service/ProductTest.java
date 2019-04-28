package pl.onsight.wypozyczalnia.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ProductTest {

    @Autowired
    private ProductService productService;
    private int sizeOfDatabase;

    @Autowired
    private SessionService sessionService;


    @Before
    public void ini() {
        sizeOfDatabase = productService.findAllProducts().size();

    }

    @Test
    @Transactional
    public void shouldFindMoreThan1FromOriginalSizeOfProducts() {
        //given
        ProductEntity product = new ProductEntity();
        productService.addProduct(product);

        //when
        List<ProductEntity> products = productService.findAllProducts();

        //then
        assertThat(products.size()).isEqualTo(sizeOfDatabase + 1);
    }

    @Test
    @Transactional
    public void shouldCheckQuantityOf44() {
        //given
        ProductEntity product = new ProductEntity();
        product.setProductName("Karabinek");
        int quantity = 44;
        product.setQuantity(quantity);
        product.setAvailable(true);
        productService.addProduct(product);

        //when
        List<CountProducts> countProducts = productService.countAllProductsByName();

        //then
        assertThat(countProducts.get(countProducts.size() - 1).getCount()).isEqualTo(quantity);
    }

    @Test
    @Transactional
    public void shouldFindProduct() {
        //given
        ProductEntity product = new ProductEntity();
        product.setProductName("Buty");
        product.setAvailable(true);
        productService.addProduct(product);

        //when
        List<CountProducts> countProducts = productService.countAllProductsByNameFiltered(product.getProductName());

        //then
        assertThat(countProducts.get(0).getProduct().getProductName()).isEqualTo(product.getProductName());
    }

    @Test
    @Transactional
    public void shouldFind2Products() {
        //given
        ProductEntity product = new ProductEntity();
        product.setProductName("Buty");
        productService.addProduct(product);

        ProductEntity product2 = new ProductEntity();
        product2.setProductName("Kask");
        productService.addProduct(product2);

        List<ProductEntity> productList = new LinkedList<>();
        productList.add(product);
        productList.add(product);
        productList.add(product2);

        //when
        List<CountProducts> countProducts = productService.changeProductEntityListToCountProductsList(productList);

        //then
        assertThat(countProducts.size()).isEqualTo(2);
    }


    @Test
    @Transactional
    public void shouldReturnListOfProductForAdminAndStaff() {
        //given
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("email", "admin"));
        UserEntity currentUser = sessionService.getCurrentUser();
        List<ProductEntity> allProducts = productService.findAllProducts();
        //when
        List<CountProducts> allProductsForAdmin = productService.createListOfCountProduct(allProducts);
        //then
        assertThat(currentUser.getRole().getRole()).isEqualTo("ROLE_ADMIN");
        assertThat(allProductsForAdmin).isNotNull();
        assertThat(allProducts.size()).isNotEqualTo(allProductsForAdmin);

    }


    @Test
    @Transactional
    public void shouldReturnListOfProductForStaff() {
        //given
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("nowak", "nowak"));
        UserEntity currentUser = sessionService.getCurrentUser();
        List<ProductEntity> allProducts = productService.findAllProducts();
        //when
        List<CountProducts> allProductsForAdmin = productService.createListOfCountProduct(allProducts);
        //then
        assertThat(currentUser.getRole().getRole()).isEqualTo("ROLE_STAFF");
        assertThat(allProductsForAdmin).isNotNull();
        assertThat(allProducts.size()).isNotEqualTo(allProductsForAdmin);

    }

    @Test
    @Transactional
    public void shouldReturnListOfProductForMember() {
        //given
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("ewa", "ewa"));
        UserEntity currentUser = sessionService.getCurrentUser();
        List<ProductEntity> allProducts = productService.findAllProducts();
        //when
        List<CountProducts> allProductsForAdmin = productService.createListOfCountProduct(allProducts);
        //then
        assertThat(currentUser.getRole().getRole()).isEqualTo("ROLE_MEMBER");
        assertThat(allProductsForAdmin).isNotNull();
        assertThat(allProducts.size()).isNotEqualTo(allProductsForAdmin);

    }

    @Test
    @Transactional
    public void shouldCountProductAvaiableByNameAndDate() {
        //given
        ProductEntity product1 = new ProductEntity();
        product1.setAvailable(true);
        product1.setQuantity(10);
        product1.setProductName("Piłka");
        product1.setPrice(10.0);

        ProductEntity product2 = new ProductEntity();
        product2.setAvailable(true);
        product2.setQuantity(10);
        product2.setProductName("Bramka");
        product2.setPrice(20.0);
        productService.addProduct(product1);
        productService.addProduct(product2);

        Date dateStart = new Date("2019/10/10 12:00");
        Date dateEnd = new Date("2019/10/15 12:00");
        //when
        Integer countOfProductWithName = productService.countProductsAvailableByNameAndTime("Piłka", dateStart, dateEnd);
        //then
        assertThat(countOfProductWithName).isGreaterThan(0);

    }

}
