package pl.onsight.wypozyczalnia.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ProductTest {

    @Autowired
    private ProductService productService;

    private int sizeOfDatabase;

    @Before
    public void addProductToDatabase() {
        sizeOfDatabase = productService.findAllProducts().size();
        ProductEntity product = new ProductEntity();
        product.setProductName("Lina");
        productService.addProductByAdmin(product);
    }

    @Test
    @Transactional
    public void shouldFindMoreThan2FromOriginalSizeOfProducts() {
        //given
        ProductEntity product = new ProductEntity();
        productService.addProductByAdmin(product);

        //when
        List<ProductEntity> products = productService.findAllProducts();

        //then
        assertThat(products.size()).isEqualTo(sizeOfDatabase + 2);
    }

    @Test
    @Transactional
    public void should() {
        //given
        ProductEntity product = new ProductEntity();
        product.setProductName("Karabinek");
        product.setQuantity(2);
        productService.addProductByAdmin(product);


        //when
        int numberOfProducts = productService.countAllProductsByName().size();

        //then
        assertThat(numberOfProducts).isEqualTo(sizeOfDatabase + 2);
    }
}
