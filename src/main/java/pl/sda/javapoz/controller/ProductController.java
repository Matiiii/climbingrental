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
    public ModelAndView showProduct(@PathVariable Long id, ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        modelAndView.addObject("product", productService.findProductById(id));
        modelAndView.addObject("productOrder", new ProductOrderEntity());
        modelAndView.addObject("tags", productService.findRelatedProducts(productService.findProductById(id)));
        return modelAndView;
    }

    @PostMapping("/product/{id}")
    public ModelAndView orderProduct(@PathVariable Long id, @ModelAttribute ProductOrderEntity productOrder, ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        ProductEntity productById = productService.findProductById(id);
        Date productOrderStart = productOrder.getOrderStart();
        Date productOrderEnd = productOrder.getOrderEnd();
        modelAndView.addObject("product", productById);
        modelAndView.addObject("productOrder", new ProductOrderEntity());
        modelAndView.addObject("tags", productService.findRelatedProducts(productService.findProductById(id)));
        UserEntity loggedUser = sessionService.getCurrentUser();

        boolean availableToOrder = productOrderService.isProductAvailableToOrder(id, productOrderStart, productOrderEnd);
        if (availableToOrder) {
            modelAndView.addObject("info", new Info("produkt zamówiony poprawnie", true));
            productOrderService.saveOrder(loggedUser, productById, productOrderStart, productOrderEnd);
        } else {
            modelAndView.addObject("info", new Info("produkt niedostępny w tym okresie", false));
        }

        return modelAndView;
    }

    @GetMapping("/products-availability/{id}")
    @ResponseBody
    public List<String> getAvailabilitiesForProduct(@PathVariable Long id) {
        return productOrderService.getListOfDatesWhenProductIsReserved(id);
    }

}