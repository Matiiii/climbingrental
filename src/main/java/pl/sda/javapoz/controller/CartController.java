package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.service.CartService;

@Controller
public class CartController {

    private CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public ModelAndView showContact(ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        modelAndView.addObject("products", cartService.getListOfProductsInBasket());
        return modelAndView;
    }
}
