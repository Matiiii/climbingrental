package pl.onsight.wypozyczalnia.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.Link;
import pl.onsight.wypozyczalnia.model.entity.AddressEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.model.entity.UserRoleEntity;

import javax.mail.Session;
import javax.management.relation.Role;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ProductTest {

  @Autowired
  private ProductService productService;
  private int sizeOfDatabase;

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
  public void shouldFound2Links() {
    //given
    ProductEntity product = new ProductEntity();
    product.setProductName("snieg");
    product.setTags("woda,ogien,zima");
    productService.addProduct(product);

    //when
    Set<Link> setOfFoundLinks = productService.findRelatedProducts(productService.findProductById((long) (sizeOfDatabase + 2)));

    //then
    assertThat(setOfFoundLinks.size()).isEqualTo(2);
  }


}
