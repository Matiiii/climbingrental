package pl.sda.javapoz.service;

import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.model.ProductOrder;
import pl.sda.javapoz.model.User;

import java.util.Date;
import java.util.List;

public interface ProductOrderService {
    void saveOrder(User userId, Product productId, Date orderStart, Date orderEnd);

    List<ProductOrder> findProductByUserId(Long id);

    ProductOrder getPriceOfOrderedProduct(ProductOrder productOrder);

    Double getPriceOfOrderedProducts(List<ProductOrder> productOrders);

    List<ProductOrder> findProductOrderByProductId(Long productId);

    boolean isProductAvailableToOrder(Long id, Date productOrderStart, Date productOrderEnd);

    List<String> getListOfDatesWhenProductIsReserved(Long id);

    List<ProductOrder> findAllProductOrders();

    void removeProductOrderByAdmin(Long id);
}
