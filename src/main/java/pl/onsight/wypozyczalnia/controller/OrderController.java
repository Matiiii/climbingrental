package pl.onsight.wypozyczalnia.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.Info;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.SessionService;

import java.util.HashSet;

@Controller
public class OrderController {

    private ProductOrderService productOrderService;
    private SessionService sessionService;


    @Autowired
    public OrderController(ProductOrderService productOrderService, SessionService sessionService) {
        this.productOrderService = productOrderService;
        this.sessionService = sessionService;
    }

    @GetMapping("/order/{id}")
    public ModelAndView getOrder(@PathVariable Long id, ModelAndView modelAndView) {

        modelAndView.setViewName("order");
        modelAndView.addObject("user", sessionService.getCurrentUser());
        if(productOrderService.isUserHavePermissionToSeeThisOrder(sessionService.getCurrentUser().getId(), id)){
            modelAndView.addObject("productOrder", productOrderService.getOrderById(id));
            modelAndView.addObject("productsHashList",  new HashSet<>(productOrderService.getOrderById(id).getProducts()));
        }else {
            modelAndView.addObject("info", new Info("Nie masz upranień by zobaczyć to zamówienie", false));
        }

        return modelAndView;
    }
}
