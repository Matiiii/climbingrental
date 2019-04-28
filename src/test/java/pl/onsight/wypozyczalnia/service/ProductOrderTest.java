package pl.onsight.wypozyczalnia.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.filter.OrderedCharacterEncodingFilter;
import org.springframework.test.context.junit4.SpringRunner;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.model.enums.Status;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.profiles.active=hsql")
public class ProductOrderTest {

  @Autowired
  private ProductOrderService productOrderService;

  @Autowired
  private ProductService productService;

  @Test
  @Transactional
  public void shouldCountNumberOfProductsInOrdersInSpecificPeriod() {
    //given
    ProductEntity product = new ProductEntity();
    String productName = "Kaktus";
    product.setProductName(productName);
    product.setQuantity(10);

    productService.addProduct(product);
    ProductEntity addedProduct = productService.findAllProducts().get(productService.findAllProducts().size() - 1);

    List<ProductEntity> firstOrderProducts = new LinkedList<>();
    firstOrderProducts.add(addedProduct);
    firstOrderProducts.add(addedProduct);
    ProductOrderEntity firstOrder = new ProductOrderEntity();
    firstOrder.setProducts(firstOrderProducts);

    Date firstOrderStartDate = new Date("2019/01/20 01:00:00");
    Date firstOrderEndDate = new Date("2019/01/23 23:59:00");
    firstOrder.setOrderStart(firstOrderStartDate);
    firstOrder.setOrderEnd(firstOrderEndDate);
    productOrderService.saveOrder(firstOrder);

    ProductOrderEntity secondOrder = new ProductOrderEntity();
    List<ProductEntity> secondOrderProducts = new LinkedList<>();
    secondOrderProducts.add(addedProduct);
    secondOrderProducts.add(addedProduct);
    secondOrderProducts.add(addedProduct);

    Date secondOrderStartDate = new Date("2019/01/25 01:00:00");
    Date secondOrderEndDate = new Date("2019/01/28 23:59:00");
    secondOrder.setOrderStart(secondOrderStartDate);
    secondOrder.setOrderEnd(secondOrderEndDate);
    secondOrder.setProducts(secondOrderProducts);
    productOrderService.saveOrder(secondOrder);

    Date dateToCheck = new Date("2019/01/10 01:00:00");
    Date dateToCheck2 = new Date("2019/01/19 23:59:00");
    Date dateToCheck3 = new Date("2019/01/23 01:00:00");
    Date dateToCheck4 = new Date("2019/01/24 23:59:00");
    Date dateToCheck5 = new Date("2019/01/24 01:00:00");

    //when
    Integer numberOfProductInOrdersInPeriod = productOrderService.countNumberOfProductInOrdersInPeriod(addedProduct, firstOrderStartDate, firstOrderEndDate);
    Integer numberOfProductInOrdersInPeriod2 = productOrderService.countNumberOfProductInOrdersInPeriod(addedProduct, dateToCheck, dateToCheck2);
    Integer numberOfProductInOrdersInPeriod3 = productOrderService.countNumberOfProductInOrdersInPeriod(addedProduct, dateToCheck3, dateToCheck4);
    Integer numberOfProductInOrdersInPeriod4 = productOrderService.countNumberOfProductInOrdersInPeriod(addedProduct, dateToCheck5, dateToCheck4);
    Integer numberOfProductInOrdersInPeriod5 = productOrderService.countNumberOfProductInOrdersInPeriod(addedProduct, dateToCheck, secondOrderEndDate);

    //then
    assertThat(addedProduct.getProductName()).isEqualTo(productName);
    assertThat(numberOfProductInOrdersInPeriod).isEqualTo(2);
    assertThat(numberOfProductInOrdersInPeriod2).isEqualTo(0);
    assertThat(numberOfProductInOrdersInPeriod3).isEqualTo(2);
    assertThat(numberOfProductInOrdersInPeriod4).isEqualTo(0);
    assertThat(numberOfProductInOrdersInPeriod5).isEqualTo(5);
  }

}
