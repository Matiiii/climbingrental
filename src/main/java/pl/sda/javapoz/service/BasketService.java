package pl.sda.javapoz.service;

import pl.sda.javapoz.model.entity.ProductEntity;

public interface BasketService {
    void addProductToBasket(ProductEntity product);
}
