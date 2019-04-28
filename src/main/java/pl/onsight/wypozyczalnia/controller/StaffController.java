package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.entity.NewsEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.service.NewsService;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.service.UserService;
import pl.onsight.wypozyczalnia.validator.ProductValidator;

@Controller
public class StaffController {

  @Controller
  public class AdminController {

    private ProductOrderService productOrderService;
    private ProductService productService;

    private UserService userService;

    @Autowired
    public AdminController(ProductOrderService productOrderService, ProductService productService, UserService userService) {
      this.productOrderService = productOrderService;
      this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/staff")
    public ModelAndView staffPage(ModelAndView modelAndView) {
      modelAndView.setViewName("staff");
      modelAndView.addObject("orderList", productOrderService.findAllProductOrders());
      modelAndView.addObject("productList", productService.findAllProducts());
      modelAndView.addObject("usersList", userService.findAllUsers());
      return modelAndView;
    }
  }
}
