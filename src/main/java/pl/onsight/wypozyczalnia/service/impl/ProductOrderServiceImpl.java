package pl.onsight.wypozyczalnia.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.repository.ProductOrderRepository;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductOrderService;
import pl.onsight.wypozyczalnia.service.UserService;

import java.util.Date;
import java.util.List;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private ProductOrderRepository productOrderRepository;
    private ProductRepository productRepository;
    private UserService userService;

    @Autowired
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, ProductRepository productRepository, UserService userService) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Override
    public void saveOrder(ProductOrderEntity order) {
        productOrderRepository.save(order);
    }

    @Override
    public Integer countNumberOfProductInOrdersInPeriod(ProductEntity product, Date productOrderStart, Date productOrderEnd) {
        List<ProductOrderEntity> orders = findProductOrdersByProductId(product.getId());
        int[] count = {0};
        orders.stream()
                .filter(order -> isProductInChosenDate(productOrderStart, productOrderEnd, order))
                .map(ProductOrderEntity::getProducts)
                .forEach(productEntities -> count[0] += productEntities.stream()
                        .filter(productEntity -> productEntity.equals(product)).count());

        return count[0];
    }

    private boolean isProductInChosenDate(Date productOrderStart, Date productOrderEnd, ProductOrderEntity order) {
        return productOrderStart.before(order.getOrderEnd()) && productOrderEnd.after(order.getOrderStart());
    }

    private List<ProductOrderEntity> findProductOrdersByProductId(Long productId) {
        ProductEntity product = productRepository.findOne(productId);
        return productOrderRepository.findAllByProductsContaining(product);
    }

    @Override
    public List<ProductOrderEntity> findAllProductOrders() {
        return productOrderRepository.findAll();
    }

    @Override
    public List<ProductOrderEntity> findUserOrders(Long id) {
        return productOrderRepository.findAllByUserId(id);
    }

    @Override
    public void removeProductOrder(Long id) {
        productOrderRepository.delete(id);
    }

    @Override
    public ProductOrderEntity getOrderById(Long id) {
        return productOrderRepository.findOne(id);
    }


    @Override
    public boolean isUserHavePermissionToSeeThisOrder(Long userId, Long orderId) {

        if (userService.getUserById(userId).getRole().equals("Admin")) {
            return true;
        } else {
            return findUserOrders(userId).stream().anyMatch(order -> order.getId().equals(orderId));
        }
    }

}