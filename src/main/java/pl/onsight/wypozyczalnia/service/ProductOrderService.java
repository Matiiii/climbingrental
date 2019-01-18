package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ProductOrderService {
    void saveOrder(ProductOrderEntity order);

    Integer countOrdersProductInPeriod(Long productId, Date productOrderStart, Date productOrderEnd);

    List<String> getListOfDatesWhenProductIsReserved(Long id);

    List<ProductOrderEntity> findAllProductOrders();

    List<ProductOrderEntity> findUserOrders(Long id);

    void removeProductOrder(Long id);
}
