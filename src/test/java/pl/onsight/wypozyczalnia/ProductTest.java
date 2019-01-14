package pl.onsight.wypozyczalnia;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductService;

import javax.transaction.Transactional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ProductTest {

    @Autowired
    private ProductService productService;

    @Before
    public void addProductToDatabase() {
        ProductEntity product = new ProductEntity();
        productService.addProductByAdmin(product);
    }

    @Test
    @Transactional
    public void shouldFind11Products() {
        //given
        ProductEntity product = new ProductEntity();
        productService.addProductByAdmin(product);

        //when
        List<ProductEntity> products = productService.findAllProducts();

        //then
        assertThat(products.size()).isEqualTo(11);
    }

}
