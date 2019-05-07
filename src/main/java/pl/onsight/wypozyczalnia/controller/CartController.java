package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.service.CartService;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.service.SessionService;
import pl.onsight.wypozyczalnia.validator.DateValidator;
import pl.onsight.wypozyczalnia.validator.OrderValidator;

import java.text.ParseException;

@Controller
@SessionAttributes("cart")
public class CartController {

    private CartService cartService;
    private ProductOrderService productOrderService;
    private SessionService sessionService;
    private ProductService productService;
    private OrderValidator orderValidator;
    private DateValidator dateValidator;

    @Autowired
    public CartController(CartService cartService, ProductOrderService productOrderService, SessionService sessionService, ProductService productService, OrderValidator orderValidator, DateValidator dateValidator) {
        this.cartService = cartService;
        this.productOrderService = productOrderService;
        this.sessionService = sessionService;
        this.productService = productService;
        this.orderValidator = orderValidator;
        this.dateValidator = dateValidator;
    }

    @GetMapping("/cart")
    public ModelAndView cartPage(@ModelAttribute("cart") Cart cart, ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        modelAndView.addObject("products", cartService.getCountedProductsInCartWithAvailable(cart));
        modelAndView.addObject("order", new ProductOrderEntity());
        modelAndView.addObject("user", sessionService.getCurrentUser());

        return modelAndView;
    }

    @PostMapping("/createOrder")
    public ModelAndView createOrder(@ModelAttribute("cart") Cart cart,
                                    ModelAndView modelAndView) throws ParseException {
        modelAndView.setViewName("cart");
        ProductOrderEntity order;
        UserEntity user = sessionService.getCurrentUser();
        if (dateValidator.isDateValid(cart.getDate())) {
            order = productOrderService.buildOrder(user, cart);
        } else {
            modelAndView.addObject("info", new Info("Data niepoprawna!", false));
            return cartPage(cart, modelAndView);
        }
        if (orderValidator.isOrderCorrectToSave(order)) {
            modelAndView.addObject("info", new Info("Zamówienie dodane poprawnie!", true));
            productOrderService.saveOrder(order);
            cartService.removeProductsFromCart(cart);
        } else {
            modelAndView.addObject("info", new Info("Zamówienie niepoprawne", false));
        }

        return cartPage(cart, modelAndView);
    }

    @PostMapping("/changeDate")
    public ModelAndView changeDate(@ModelAttribute("cart") Cart cart,
                                   @RequestParam(value = "datefilter", defaultValue = "") String dateFilter,
                                   ModelAndView modelAndView) throws ParseException {

        if (!dateValidator.isDateValid(dateFilter)) {
            modelAndView.addObject("info", new Info("Data niepoprawna!", false));
            return cartPage(cart, modelAndView);
        }

        cartService.addDateToCart(cart, dateFilter);
        return cartPage(cart, modelAndView);
    }

    @PostMapping("/cart/deleteProduct/{id}")
    public ModelAndView deleteProductFromCart(@PathVariable Long id,
                                              @ModelAttribute("cart") Cart cart,
                                              ModelAndView modelAndView) {

        modelAndView.addObject("info", new Info("Produkt usunięty poprawnie!", true));
        cartService.removeProductFromCart(cart, productService.findProductById(id));
        return cartPage(cart, modelAndView);
    }

    @PostMapping("/cart/deleteAllProductsOfOneType/{id}")
    public ModelAndView deleteProductsOneTypeFromCart(@PathVariable Long id,
                                                      @ModelAttribute("cart") Cart cart,
                                                      ModelAndView modelAndView) {
        modelAndView.addObject("info", new Info("Produkty usunięte poprawnie!", true));
        cartService.removeProductsOneTypeFromCart(cart, productService.findProductById(id));
        return cartPage(cart, modelAndView);
    }

    @PostMapping("/cart/addProduct/{id}")
    public ModelAndView addProductToCart(@PathVariable Long id,
                                         @ModelAttribute("cart") Cart cart,
                                         ModelAndView modelAndView) {

        modelAndView.addObject("info", new Info("Produkt dodany poprawnie!", true));
        cartService.addProductToCart(cart, productService.findProductById(id), 1);

        return cartPage(cart, modelAndView);
    }


    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }
}

