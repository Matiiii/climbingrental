package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import javax.validation.Valid;

@Controller
public class AdminController {

    private ProductOrderService productOrderService;
    private ProductService productService;

    @Autowired
    public AdminController(ProductOrderService productOrderService, ProductService productService) {
        this.productOrderService = productOrderService;
        this.productService = productService;
    }

    @GetMapping("/admin-page")
    public ModelAndView adminPage(ModelAndView modelAndView) {
        modelAndView.setViewName("admin");
        modelAndView.addObject("orderList", productOrderService.findAllProductOrders());
        modelAndView.addObject("productList", productService.findAllProducts());
        modelAndView.addObject("addProduct", new ProductEntity());
        return modelAndView;
    }

    @DeleteMapping(path = "/product-order/{id}")
    @ResponseBody
    public void removeProductOrder(@PathVariable Long id) {
        productOrderService.removeProductOrder(id);
    }

    @DeleteMapping(path = "/product/{id}")
    @ResponseBody
    public void removeProduct(@PathVariable Long id) {
        productService.removeProduct(id);
    }


    @PostMapping("/admin-page")
    public ModelAndView addProduct(@ModelAttribute @Valid ProductEntity product,
                                   BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("admin");
        } else {
            productService.addProduct(product);
            modelAndView.setViewName("redirect:/shop");
        }
        return modelAndView;
    }
}
