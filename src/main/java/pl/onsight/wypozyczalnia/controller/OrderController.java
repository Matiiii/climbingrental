package pl.onsight.wypozyczalnia.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.NewsEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.service.PdfService;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.SessionService;
import pl.onsight.wypozyczalnia.validator.UserValidator;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashSet;

@Controller
public class OrderController {
  private ProductOrderService productOrderService;
  private UserValidator userValidator;
  private SessionService sessionService;
  private PdfService pdfService;

  @Autowired
  public OrderController(ProductOrderService productOrderService, UserValidator userValidator, SessionService sessionService, PdfService pdfService) {
    this.productOrderService = productOrderService;
    this.userValidator = userValidator;
    this.sessionService = sessionService;
    this.pdfService = pdfService;
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


/*  @PostMapping("/printOrder/{id}")
  public ModelAndView convertOrderToPdf(@Valid ProductOrderEntity order, ModelAndView modelAndView) throws IOException {
    pdfService.generateHtmlToPdfOrder(order.getId());
    getOrder(order.getId(), modelAndView);
    modelAndView.setViewName("redirect:/shop");
    return modelAndView;
  }*/

}
