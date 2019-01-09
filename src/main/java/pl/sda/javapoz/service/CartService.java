package pl.sda.javapoz.service;

import pl.sda.javapoz.model.entity.ProductEntity;

import java.util.List;

public interface CartService {
    void addProductToCart(ProductEntity product, int quantity);

    List<ProductEntity> getListOfProductsInCart();
}
