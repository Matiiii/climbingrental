package pl.onsight.wypozyczalnia.validator;

import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

public interface ProductValidator {

    boolean isNameUsed(ProductEntity product);
}
