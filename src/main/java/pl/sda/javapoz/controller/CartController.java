package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.entity.ProductOrderEntity;
import pl.sda.javapoz.model.entity.UserEntity;
import pl.sda.javapoz.service.CartService;
import pl.sda.javapoz.service.ProductOrderService;
import pl.sda.javapoz.service.SessionService;

@Controller
public class CartController {

    private CartService cartService;
    private ProductOrderService productOrderService;
    private SessionService sessionService;

    @Autowired
    public CartController(CartService cartService, ProductOrderService productOrderService, SessionService sessionService) {
        this.cartService = cartService;
        this.productOrderService = productOrderService;
        this.sessionService = sessionService;
    }

    @GetMapping("/cart")
    public ModelAndView cartTemplate(ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        modelAndView.addObject("products", cartService.getListOfProductsInCart());
        modelAndView.addObject("order", new ProductOrderEntity());
        return modelAndView;
    }

    @PostMapping("/makeorder")
    public ModelAndView makeOrder(@ModelAttribute("order") ProductOrderEntity order, ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        UserEntity user = sessionService.getCurrentUser();
        order.setUserId(user);
        order.setProducts(cartService.getListOfProductsInCart());
        productOrderService.saveOrder(order);
        return modelAndView;
    }
}
