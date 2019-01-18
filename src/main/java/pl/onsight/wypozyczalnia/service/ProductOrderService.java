package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ProductOrderService {
    void saveOrder(ProductOrderEntity order);

    Integer countNumberOfProductInOrdersInPeriod(ProductEntity product, Date productOrderStart, Date productOrderEnd);

    List<ProductOrderEntity> findAllProductOrders();

    List<ProductOrderEntity> findUserOrders(Long id);

    void removeProductOrder(Long id);
}
