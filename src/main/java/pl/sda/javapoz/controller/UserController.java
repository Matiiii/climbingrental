package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.sda.javapoz.model.ProductOrder;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.repository.UserRepository;
import pl.sda.javapoz.service.NavbarLinkService;
import pl.sda.javapoz.service.ProductOrderService;
import pl.sda.javapoz.service.SessionService;
import pl.sda.javapoz.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by pablo on 22.03.17.
 */
@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    private NavbarLinkService navbarLinkService;

    @Autowired
    ProductOrderService productOrdersService;

    @Autowired
    private SessionService sessionService;


    @RequestMapping("/user")
    public ModelAndView getUserPage(Authentication authentication, Principal principal) {

        ModelAndView modelAndView = new ModelAndView("user");
        modelAndView.addObject("navbarLinks", navbarLinkService.fetchLinks());
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
