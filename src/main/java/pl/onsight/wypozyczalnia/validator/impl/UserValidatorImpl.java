package pl.onsight.wypozyczalnia.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.UserService;
import pl.onsight.wypozyczalnia.validator.UserValidator;

@Component
public class UserValidatorImpl implements UserValidator {

    private UserService userService;
    private ProductOrderService productOrderService;

    @Autowired
    public UserValidatorImpl(UserService userService, ProductOrderService productOrderService) {
        this.userService = userService;
        this.productOrderService = productOrderService;
    }

    @Override
    public boolean isUserHavePermissionToSeeThisOrder(Long userId, Long orderId) {

        if (userService.getUserById(userId).getRole().getRole().equals("ROLE_ADMIN")) {
            return true;
        } else {
            return productOrderService.findUserOrders(userId).stream().anyMatch(order -> order.getId().equals(orderId));
        }
    }
}
