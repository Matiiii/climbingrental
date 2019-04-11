package pl.onsight.wypozyczalnia.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.DatabaseInitialization;
import pl.onsight.wypozyczalnia.service.ProductOrderService;

import java.util.Date;

@Component
public class ScheduledTask {

  private ProductOrderService productOrderService;

  @Autowired
  public ScheduledTask(ProductOrderService productOrderService) {
    this.productOrderService = productOrderService;
  }

  @Scheduled(fixedRate = 60 * 60 * 1000)
  public void excecuteTask() {
    Date date=new Date();
    System.out.println("Done at: "+date);
    productOrderService.changeStatusOfUnpaidOrder();
  }

}
