package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.service.CartService;
import pl.sda.javapoz.service.ProductOrderService;

@Controller
public class CartController {

    private CartService cartService;
    private ProductOrderService productOrderService;

    @Autowired
    public CartController(CartService cartService, ProductOrderService productOrderService) {
        this.cartService = cartService;
        this.productOrderService = productOrderService;
    }

    @GetMapping("/cart")
    public ModelAndView cartTemplate(ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        modelAndView.addObject("products", cartService.getListOfProductsInCart());
        return modelAndView;
    }

    @PostMapping("/makeorder")
    public ModelAndView makeOrder(ModelAndView modelAndView){
        modelAndView.setViewName("cart");
        productOrderService.saveOrder(cartService.getListOfProductsInCart());
        return modelAndView;
    }
}
