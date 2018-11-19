package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.Info;
import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.model.ProductOrder;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.service.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by RENT on 2017-03-22.
 */

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductOrderService productOrderService;

    @Autowired
    private NavbarLinkService navbarLinkService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping("/product/{id}")
    public ModelAndView showProduct(@PathVariable Long id) {

        ModelAndView modelAndView = new ModelAndView("product");
        modelAndView.addObject("product", productService.findProductById(id));
        modelAndView.addObject("productOrder", new ProductOrder());
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
        modelAndView.addObject("tags", productService.findRelatedProducts(productService.findProductById(id)));
        return modelAndView;
    }

    @PostMapping("/product/{id}")
    public ModelAndView orderProduct(@PathVariable Long id, @ModelAttribute ProductOrder productOrder) {

        ModelAndView modelAndView = new ModelAndView("product");
        Product productById = productService.findProductById(id);
        Date productOrderStart = productOrder.getOrderStart();
        Date productOrderEnd = productOrder.getOrderEnd();
        modelAndView.addObject("product", productById);
        modelAndView.addObject("productOrder", new ProductOrder());
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
        modelAndView.addObject("tags", productService.findRelatedProducts(productService.findProductById(id)));
        User loggedUser = sessionService.getCurrentUser();

        boolean availableToOrder = productOrderService.isProductAvailableToOrder(id,productOrderStart,productOrderEnd);
        if (availableToOrder){
            modelAndView.addObject("info", new Info("produkt zamówiony poprawnie",true));
            productOrderService.saveOrder(loggedUser, productById, productOrderStart, productOrderEnd);
        } else {
            modelAndView.addObject("info", new Info("produkt niedostępny w tym okresie",false));
        }

        return modelAndView;
    }

    @GetMapping("/products-availability/{id}")
    @ResponseBody
    public List<String> getAvailabilitiesForProduct(@PathVariable Long id) {
        List<String> dates = productOrderService.getListOfDatesWhenProductIsReserved(id);
        return dates;
    }

}