package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.service.ProductService;

@Controller
public class StaffController {

  private ProductService productService;

  @Autowired
  public StaffController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping("/staff")
  public ModelAndView staffPage(ModelAndView modelAndView) {
    modelAndView.setViewName("staff");
    modelAndView.addObject("allProduct", productService.findAllProducts());
    return modelAndView;
  }
}
