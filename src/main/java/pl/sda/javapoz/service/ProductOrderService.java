package pl.sda.javapoz.service;

import pl.sda.javapoz.model.entity.ProductEntity;
import pl.sda.javapoz.model.entity.ProductOrderEntity;
import pl.sda.javapoz.model.entity.UserEntity;

import java.util.Date;
import java.util.List;

public interface ProductOrderService {
    void saveOrder(UserEntity userId, Date orderStart, Date orderEnd);

    void saveOrder(UserEntity user, List<ProductEntity> products);

    void saveOrder(ProductOrderEntity order);

    List<ProductOrderEntity> findProductsByUserId(Long id);

    ProductOrderEntity getPriceOfOrderedProduct(ProductOrderEntity productOrder);

    Double getPriceOfOrderedProducts(List<ProductOrderEntity> productOrders);

    List<ProductOrderEntity> findProductOrderByProductId(Long productId);

    boolean isProductAvailableToOrder(Long id, Date productOrderStart, Date productOrderEnd);

    boolean isProductAvailableToOrder(Long id, String dateFilter);

    List<String> getListOfDatesWhenProductIsReserved(Long id);

    List<ProductOrderEntity> findAllProductOrders();

    void removeProductOrder(Long id);
}
