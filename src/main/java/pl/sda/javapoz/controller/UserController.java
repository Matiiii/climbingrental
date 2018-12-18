package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.ProductOrder;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.service.ProductOrderService;
import pl.sda.javapoz.service.SessionService;

import java.security.Principal;
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
    public ModelAndView getUserPage(Authentication authentication, Principal principal) {

        ModelAndView modelAndView = new ModelAndView("user");
        User currentUser = sessionService.getCurrentUser();
        modelAndView.addObject("user", currentUser);
        List<ProductOrder> productByUserId = productOrdersService.findProductByUserId(currentUser.getId());
        productByUserId = productByUserId.stream()
                .map(e -> productOrdersService.getPriceOfOrderedProduct(e))
                .collect(Collectors.toList());
        modelAndView.addObject("orders", productByUserId);
        modelAndView.addObject("combinedPrice", productOrdersService.getPriceOfOrderedProducts(productByUserId));
        return modelAndView;
    }
}
