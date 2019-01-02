package pl.sda.javapoz.service;

import pl.sda.javapoz.model.ProductEntity;
import pl.sda.javapoz.model.ProductOrderEntity;
import pl.sda.javapoz.model.UserEntity;

import java.util.Date;
import java.util.List;

public interface ProductOrderService {
    void saveOrder(UserEntity userId, ProductEntity productId, Date orderStart, Date orderEnd);

    List<ProductOrderEntity> findProductsByUserId(Long id);

    ProductOrderEntity getPriceOfOrderedProduct(ProductOrderEntity productOrder);

    Double getPriceOfOrderedProducts(List<ProductOrderEntity> productOrders);

    List<ProductOrderEntity> findProductOrderByProductId(Long productId);

    boolean isProductAvailableToOrder(Long id, Date productOrderStart, Date productOrderEnd);

    List<String> getListOfDatesWhenProductIsReserved(Long id);

    List<ProductOrderEntity> findAllProductOrders();

    void removeProductOrderByAdmin(Long id);
}
