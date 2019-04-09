package pl.onsight.wypozyczalnia.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.model.enums.Status;
import pl.onsight.wypozyczalnia.repository.ProductOrderRepository;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductOrderService;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

  private ProductOrderRepository productOrderRepository;
  private ProductRepository productRepository;
  private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


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

  @Override
  public void changeStatusOfUnpaidOrder() {
    List<ProductOrderEntity> allOrders = productOrderRepository.findAll();
    List<ProductOrderEntity> unpaidOrders = new ArrayList<>();
    Date date = new Date();

    for (ProductOrderEntity order : allOrders) {
      if (!order.isPaid() && order.getOrderStart().after(date)) {
        unpaidOrders.add(order);
      }
    }
    for (ProductOrderEntity orderAfter : unpaidOrders) {
      orderAfter.setStatusOfOrder(Status.NIEZAPŁACONE);
    }

  }



 /* @Override
  public void removeUnpaidOrder(Long orderId) {
    ProductOrderEntity orderEntity = productOrderRepository.findOne(orderId);
    Date date = new Date();
    *//*String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    simpleDateFormat.format(date);*//*
    Date dateToCheck = orderEntity.getOrderStart();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dateToCheck);
    calendar.add(Calendar.DATE, 3);
    if (dateToCheck.after(date) && !orderEntity.isPaid()) {
      orderEntity.setStatusOfOrder(Status.NIEZAPŁACONE);
    }
  }*/


}
