package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.Info;
import pl.sda.javapoz.model.ProductEntity;
import pl.sda.javapoz.model.ProductOrderEntity;
import pl.sda.javapoz.model.UserEntity;
import pl.sda.javapoz.service.ProductOrderService;
import pl.sda.javapoz.service.ProductService;
import pl.sda.javapoz.service.SessionService;

import java.util.Date;
import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;
    private ProductOrderService productOrderService;
    private SessionService sessionService;

    @Autowired
    public ProductController(ProductService productService, ProductOrderService productOrderService, SessionService sessionService) {
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.sessionService = sessionService;
    }

    @GetMapping("/product/{id}")
    public ModelAndView productPage(@PathVariable Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        modelAndView.addObject("product", productService.findProductById(id));
        modelAndView.addObject("productOrder", new ProductOrderEntity());
        modelAndView.addObject("tags", productService.findRelatedProducts(productService.findProductById(id)));
        return modelAndView;
    }

    @PostMapping("/product/{id}")
    public ModelAndView addProductToCart(@PathVariable Long id,
                                     @RequestParam(value = "productCount") String productCount,
                                     @RequestParam(value = "productName") String productName,
                                     ModelAndView modelAndView) {
        modelAndView.setViewName("product");

        if (productCount.isEmpty() || Integer.parseInt(productCount) < 1) {
            modelAndView.addObject("info", new Info("Nieprawidłowa ilość ", false));
        } else {
            modelAndView.addObject("info", new Info("Dodano do koszyka " + productCount + " " + productName , true));
        }

        return productPage(id, modelAndView);
    }

    @GetMapping("/products-availability/{id}")
    @ResponseBody
    public List<String> getAvailabilitiesForProduct(@PathVariable Long id) {
        return productOrderService.getListOfDatesWhenProductIsReserved(id);
    }

}