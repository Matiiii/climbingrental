package pl.sda.javapoz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
import java.util.stream.Collectors;

@Controller
public class UserController {

    private UserRepository userRepository;
    private UserService userService;
    private NavbarLinkService navbarLinkService;
    private ProductOrderService productOrdersService;
    private SessionService sessionService;

    @Autowired
    public UserController(UserRepository userRepository, UserService userService, NavbarLinkService navbarLinkService, ProductOrderService productOrdersService, SessionService sessionService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.navbarLinkService = navbarLinkService;
        this.productOrdersService = productOrdersService;
        this.sessionService = sessionService;
    }

    @GetMapping("/user")
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
