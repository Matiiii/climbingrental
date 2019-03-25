package pl.onsight.wypozyczalnia.validator;

import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;

public interface OrderValidator {
    boolean isOrderCorrectToSave(ProductOrderEntity order);

}
