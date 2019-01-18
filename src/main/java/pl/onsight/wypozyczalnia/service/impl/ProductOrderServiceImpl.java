package pl.onsight.wypozyczalnia.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.repository.ProductOrderRepository;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductOrderService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private ProductOrderRepository productOrderRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, ProductRepository productRepository) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void saveOrder(ProductOrderEntity order) {
        productOrderRepository.save(order);
    }

    @Override
    public Integer countNumberOfProductInOrdersInPeriod(Long productId, Date productOrderStart, Date productOrderEnd) {
        List<ProductOrderEntity> orders = findProductOrdersByProductId(productId);
        int count = 0;
        for (ProductOrderEntity order : orders) {
            Date orderStart = order.getOrderStart();
            Date orderEnd = order.getOrderEnd();
            if (productOrderStart.before(orderEnd) && productOrderEnd.after(orderStart)) {
                count += order.getProducts().stream()
                        .filter(productEntity -> productEntity.getId().equals(productId))
                        .collect(Collectors.toList())
                        .size();
            }
        }

        return count;
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
}