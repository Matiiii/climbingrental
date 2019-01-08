package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.Info;
import pl.sda.javapoz.model.entity.ProductEntity;
import pl.sda.javapoz.model.entity.ProductOrderEntity;
import pl.sda.javapoz.service.CartService;
import pl.sda.javapoz.service.ProductOrderService;
import pl.sda.javapoz.service.ProductService;
import pl.sda.javapoz.service.SessionService;

import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;
    private ProductOrderService productOrderService;
    private SessionService sessionService;
    private CartService cartService;

    @Autowired
    public ProductController(ProductService productService, ProductOrderService productOrderService, SessionService sessionService, CartService cartService) {
        this.productService = productService;
        this.productOrderService = productOrderService;
        this.sessionService = sessionService;
        this.cartService = cartService;
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
                                     @RequestParam(value = "productCount") Integer productCount,
                                     @RequestParam(value = "productName") String productName,
                                     ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        ProductEntity productById = productService.findProductById(id);

        //boolean availableToOrder = productOrderService.isProductAvailableToOrder(id, dateFilter);
        if (productCount == null) {
            productCount = 1;
        }
        else if(productCount < 0){
            modelAndView.addObject("info", new Info("Nieprawidłowa ilość", false));
            return productPage(id, modelAndView);
        }

        modelAndView.addObject("info", new Info("Dodano do koszyka " + productCount + " " + productName , true));
        cartService.addProductToCart(productById, productCount);


/*        if (availableToOrder) {
            modelAndView.addObject("info", new Info("produkt zamówiony poprawnie", true));
            cartService.addProductToCart(productById);
        } else {
            modelAndView.addObject("info", new Info("produkt niedostępny w tym okresie", false));
        }*/

        return productPage(id, modelAndView);
    }

 /*   @PostMapping("/product/{id}")
    public ModelAndView addProductToCart(@PathVariable("id") Long id, @RequestParam(value = "datefilter", defaultValue = "") String dateFilter, ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        ProductEntity productById = productService.findProductById(id);
        UserEntity loggedUser = sessionService.getCurrentUser();

        boolean availableToOrder = productOrderService.isProductAvailableToOrder(id, dateFilter);
        if (availableToOrder) {
            modelAndView.addObject("info", new Info("produkt zamówiony poprawnie", true));
            cartService.addProductToCart(productById);
        } else {
            modelAndView.addObject("info", new Info("produkt niedostępny w tym okresie", false));
        }

        return productPage(id, modelAndView);
    }*/

    @GetMapping("/products-availability/{id}")
    @ResponseBody
    public List<String> getAvailabilitiesForProduct(@PathVariable Long id) {
        return productOrderService.getListOfDatesWhenProductIsReserved(id);
    }

}