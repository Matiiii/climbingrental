package pl.onsight.wypozyczalnia.model;

import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import java.util.List;

public class PayUObject {

    //required
    String customerIp;
    String merchantPosId;
    String description;
    String currencyCode;
    String totalAmount;
    String buyer;
    List<ProductEntity> products;

    //not required
    String extOrderId;
    String notifyUrl;
    String validityTime;
    String additionalDescription;
    String continueUrl;
    String payMethods;
}
