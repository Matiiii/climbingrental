package pl.onsight.wypozyczalnia.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.model.enums.Status;
import pl.onsight.wypozyczalnia.repository.ProductOrderRepository;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductOrderService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

  private ProductOrderRepository productOrderRepository;
  private ProductRepository productRepository;

  @Autowired
  public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, ProductRepository productRepository) {
    this.productOrderRepository = productOrderRepository;
    this.productRepository = productRepository;
  }

  @Override
  public void saveOrder(ProductOrderEntity order) {
    productOrderRepository.save(order);
  }

  @Override
  public Integer countNumberOfProductInOrdersInPeriod(ProductEntity product, Date productOrderStart, Date productOrderEnd) {
    List<ProductOrderEntity> orders = findProductOrdersByProductId(product.getId());
    int[] count = {0};
    orders.stream()
      .filter(order -> isProductInChosenDate(productOrderStart, productOrderEnd, order))
      .map(ProductOrderEntity::getProducts)
      .forEach(productEntities -> count[0] += productEntities.stream()
        .filter(productEntity -> productEntity.equals(product)).count());

    return count[0];
  }

  private boolean isProductInChosenDate(Date productOrderStart, Date productOrderEnd, ProductOrderEntity order) {
    return productOrderStart.before(order.getOrderEnd()) && productOrderEnd.after(order.getOrderStart());
  }

  private List<ProductOrderEntity> findProductOrdersByProductId(Long productId) {
    ProductEntity product = productRepository.findOne(productId);
    return productOrderRepository.findAllByProductsContaining(product);
  }

  @Override
  public List<ProductOrderEntity> findAllProductOrders() {
    return productOrderRepository.findAll();
  }

  @Override
  public List<ProductOrderEntity> findUserOrders(Long id) {
    return productOrderRepository.findAllByUserId(id);
  }

  @Override
  public void removeProductOrder(Long id) {
    productOrderRepository.delete(id);
  }

  @Override
  public ProductOrderEntity getOrderById(Long id) {
    return productOrderRepository.findOne(id);
  }

  @Override
  public ProductOrderEntity buildOrder(UserEntity user, Cart cart) {
    ProductOrderEntity order = new ProductOrderEntity();

    order.setUser(user);
    order.setProducts(cart.getProducts());
    order.setOrderStart(DateFilter.changeStringToDate(cart.getDate())[0]);
    order.setOrderEnd(DateFilter.changeStringToDate(cart.getDate())[1]);
    order.setCombinedPrice(cart.getPriceWithDiscount(user));
    order.setDeposit(cart.getCombinedDeposit());
    order.setCombinedDiscount(user.getRole().getDiscount());
    order.createOldPricesHashMap(cart);

    return order;
  }

  private Date addHoursToCurrentOrderDate(Date date, int minutes) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.MINUTE, minutes);
    //calendar.add(Calendar.HOUR_OF_DAY, hours);
    return calendar.getTime();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED, readOnly = true, noRollbackFor = Exception.class)
  public void changeStatusOfUnpaidOrder() {
    List<ProductOrderEntity> allOrders = findAllProductOrders();
    Date date = new Date();
    for (ProductOrderEntity order : allOrders) {
      Date dateToCheck = addHoursToCurrentOrderDate(order.getOrderStart(), 1);
      if (!order.isPaid() && dateToCheck.after(date)) {
        order.setStatusOfOrder(Status.CANCELED_DUE_TO_NON_PAYMENT);
        System.out.println(order.getStatusOfOrder());
        productOrderRepository.save(order);
        System.out.println("zostal zapisany");
      }
    }
  }

}
