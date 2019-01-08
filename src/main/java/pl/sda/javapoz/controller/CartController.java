package pl.sda.javapoz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CartController {
    @GetMapping("/cart")
    public ModelAndView showContact(ModelAndView modelAndView) {
        modelAndView.setViewName("cart");
        return modelAndView;
    }
}