package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ProductOrderService {
    void saveOrder(ProductOrderEntity order);

    Integer countNumberOfProductInOrdersInPeriod(Long productId, Date productOrderStart, Date productOrderEnd);

    List<ProductOrderEntity> findAllProductOrders();

    List<ProductOrderEntity> findUserOrders(Long id);

    void removeProductOrder(Long id);
}
