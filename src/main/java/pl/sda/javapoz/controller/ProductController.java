package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.Info;
import pl.sda.javapoz.model.entity.ProductEntity;
import pl.sda.javapoz.model.entity.ProductOrderEntity;
import pl.sda.javapoz.model.entity.UserEntity;
import pl.sda.javapoz.service.CartService;
import pl.sda.javapoz.service.ProductOrderService;
import pl.sda.javapoz.service.ProductService;
import pl.sda.javapoz.service.SessionService;

import javax.jws.WebParam;
import javax.validation.Valid;
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
                                         @RequestParam(value = "productCount") String productCount,
                                         @RequestParam(value = "productName") String productName,
                                         ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        ProductEntity productById = productService.findProductById(id);

        //boolean availableToOrder = productOrderService.isProductAvailableToOrder(id, dateFilter);
        if (productCount.isEmpty() || Integer.parseInt(productCount) < 1) {
            modelAndView.addObject("info", new Info("Nieprawidłowa ilość ", false));
        } else {
            modelAndView.addObject("info", new Info("Dodano do koszyka " + productCount + " " + productName, true));
            cartService.addProductToBasket(productById);
        }

/*        if (availableToOrder) {
            modelAndView.addObject("info", new Info("produkt zamówiony poprawnie", true));
            cartService.addProductToBasket(productById);
        } else {
            modelAndView.addObject("info", new Info("produkt niedostępny w tym okresie", false));
        }*/

        return productPage(id, modelAndView);
    }

 /*   @PostMapping("/product/{id}")
    public ModelAndView addProductToBasket(@PathVariable("id") Long id, @RequestParam(value = "datefilter", defaultValue = "") String dateFilter, ModelAndView modelAndView) {
        modelAndView.setViewName("product");
        ProductEntity productById = productService.findProductById(id);
        UserEntity loggedUser = sessionService.getCurrentUser();

        boolean availableToOrder = productOrderService.isProductAvailableToOrder(id, dateFilter);
        if (availableToOrder) {
            modelAndView.addObject("info", new Info("produkt zamówiony poprawnie", true));
            cartService.addProductToBasket(productById);
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

    @DeleteMapping(value = "/product/remove/{id}")
    public ModelAndView removeProduct(@RequestParam("id") Long id, ModelAndView modelAndView) {
        productService.removeProduct(id);
        modelAndView.setViewName("shopProducts");
        return modelAndView;
    }

    @GetMapping("/product/add")
    public ModelAndView addingPage(ModelAndView modelAndView) {
        modelAndView.setViewName("addProductToCollection");
        modelAndView.addObject("newProduct", new ProductEntity());
        return modelAndView;
    }

    @PostMapping("/product/add")
    public ModelAndView addUser(@ModelAttribute @Valid ProductEntity product,
                                BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("shop");
        } else {
            productService.addProduct(product);
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }
}