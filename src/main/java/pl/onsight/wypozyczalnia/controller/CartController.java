package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.service.CartService;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.service.SessionService;
import pl.onsight.wypozyczalnia.validator.OrderValidator;

@Controller
@SessionAttributes("cart")
public class CartController {

    private CartService cartService;
    private ProductOrderService productOrderService;
    private SessionService sessionService;
    private ProductService productService;
    private OrderValidator orderValidator;

    @Autowired
    public CartController(CartService cartService, ProductOrderService productOrderService, SessionService sessionService, ProductService productService, OrderValidator orderValidator) {
        this.cartService = cartService;
        this.productOrderService = productOrderService;
        this.sessionService = sessionService;
        this.productService = productService;
        this.orderValidator = orderValidator;
    }

    @GetMapping("/cart")
    public ModelAndView cartPage(@ModelAttribute("cart") Cart cart, ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        modelAndView.addObject("products", productService.countProductsInProductList(cartService.getListOfProductsInCart(cart)));
        modelAndView.addObject("order", new ProductOrderEntity());
        return modelAndView;
    }

    @PostMapping("/createOrder")
    public ModelAndView createOrder(@ModelAttribute("order") ProductOrderEntity order,
                                    @ModelAttribute("cart") Cart cart,
                                    ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        UserEntity user = sessionService.getCurrentUser();
        order.setUser(user);
        order.setProducts(cartService.getListOfProductsInCart(cart));
        order.setOrderStart(DateFilter.changeStringToDate(cart.getDate())[0]);
        order.setOrderEnd(DateFilter.changeStringToDate(cart.getDate())[1]);

        if (orderValidator.isOrderAvailableToSave(order)) {
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
                                   ModelAndView modelAndView) {
        cartService.addDateToCart(cart, dateFilter);
        return cartPage(cart, modelAndView);
    }

    @ModelAttribute("cart")
    public Cart cart() {
        return new Cart();
    }
}

