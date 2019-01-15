package pl.onsight.wypozyczalnia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.model.entity.UserEntity;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.SessionService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private ProductOrderService productOrdersService;
    private SessionService sessionService;

    @Autowired
    public UserController(ProductOrderService productOrdersService, SessionService sessionService) {
        this.productOrdersService = productOrdersService;
        this.sessionService = sessionService;
    }

    @GetMapping("/user")
    public ModelAndView userPage(ModelAndView modelAndView) {
        modelAndView.setViewName("user");
        UserEntity currentUser = sessionService.getCurrentUser();
        modelAndView.addObject("user", currentUser);
        return modelAndView;
    }
}
