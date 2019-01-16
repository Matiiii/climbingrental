package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface ProductOrderService {
    void saveOrder(ProductOrderEntity order);

    List<ProductOrderEntity> findProductsByUserId(Long id);

    ProductOrderEntity getPriceOfOrderedProduct(ProductOrderEntity productOrder);

    Double getPriceOfOrderedProducts(List<ProductOrderEntity> productOrders);

    boolean isProductAvailableToOrder(Long id, Date productOrderStart, Date productOrderEnd);

    boolean isProductAvailableToOrder(Long id, String dateFilter) throws ParseException;

    Integer countOrdersProductInPeriod(Long productId, Date productOrderStart, Date productOrderEnd);

    List<String> getListOfDatesWhenProductIsReserved(Long id);

    List<ProductOrderEntity> findAllProductOrders();

    void removeProductOrder(Long id);
}
