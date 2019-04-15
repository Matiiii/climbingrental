package pl.onsight.wypozyczalnia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.pdfgenerator.PdfGeneratorUtil;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.SessionService;
import pl.onsight.wypozyczalnia.validator.UserValidator;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Controller
public class OrderController {
  private ProductOrderService productOrderService;
  private UserValidator userValidator;
  private SessionService sessionService;
  private PdfGeneratorUtil pdfGeneratorUtil;

  @Autowired
  public OrderController(ProductOrderService productOrderService, UserValidator userValidator, SessionService sessionService, PdfGeneratorUtil pdfGeneratorUtil) {
    this.productOrderService = productOrderService;
    this.userValidator = userValidator;
    this.sessionService = sessionService;
    this.pdfGeneratorUtil = pdfGeneratorUtil;
  }

  @GetMapping("/order/{id}")
  public ModelAndView getOrder(@PathVariable("id") Long id, ModelAndView modelAndView) {
    modelAndView.setViewName("order");
    modelAndView.addObject("user", sessionService.getCurrentUser());
    if (userValidator.isUserHavePermissionToSeeThisOrder(sessionService.getCurrentUser().getId(), id)) {
      modelAndView.addObject("productOrder", productOrderService.getOrderById(id));
      modelAndView.addObject("productsHashList", new HashSet<>(productOrderService.getOrderById(id).getProducts()));
    } else {
      modelAndView.addObject("info", new Info("Nie masz upranień by zobaczyć to zamówienie", false));
    }
    return modelAndView;
  }


  @GetMapping("/printOrder/{id}")
  public ModelAndView orderSummary(@PathVariable("id") Long id, ModelAndView modelAndView) {
    modelAndView.setViewName("orderSummary");
    modelAndView.addObject("user", sessionService.getCurrentUser());
    modelAndView.addObject("productOrder", productOrderService.getOrderById(id));
    modelAndView.addObject("products", productOrderService.getOrderById(id).getProducts());
    modelAndView.addObject("productsHashList", new HashSet<>(productOrderService.getOrderById(id).getProducts()));
    return modelAndView;
  }

  @PostMapping("/printOrder/{id}")
  public ModelAndView convertOrderToPdf(@PathVariable Long id, ModelAndView modelAndView) throws Exception {
    ProductOrderEntity order = productOrderService.getOrderById(id);

    Map<String, Object> data = new HashMap<>();
    data.put("orderId", id);
    data.put("date", new Date().toString());
    data.put("dateStart", order.getOrderStart());
    data.put("dateEnd", order.getOrderEnd());
    data.put("firstName", order.getUser().getFirstName());
    data.put("lastName", order.getUser().getLastName());
    data.put("email", order.getUser().getEmail());
    data.put("phone", order.getUser().getPhoneNumber());
    data.put("adress", order.getUser().getAddress());
    data.put("price", order.getCombinedPrice());
    data.put("products", order.getProducts());
    data.put("days", order.getNumberOfDays());
    pdfGeneratorUtil.createPdf("orderSummary", data, id);

    orderSummary(order.getId(), modelAndView);
    modelAndView.setViewName("redirect:/shop");

    return modelAndView;
  }

}
