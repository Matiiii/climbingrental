package pl.sda.javapoz.service;

import pl.sda.javapoz.model.entity.ProductEntity;

import java.util.List;

public interface CartService {
    void addProductToBasket(ProductEntity product);

    List<ProductEntity> getListOfProductsInBasket();
}
