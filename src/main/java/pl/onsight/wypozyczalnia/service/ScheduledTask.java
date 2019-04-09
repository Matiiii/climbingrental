package pl.onsight.wypozyczalnia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

  private ProductOrderService productOrderService;

  @Autowired
  public ScheduledTask(ProductOrderService productOrderService) {
    this.productOrderService = productOrderService;
  }

  @Scheduled
  public void excecuteTask() {
    productOrderService.changeStatusOfUnpaidOrder();
  }

}
