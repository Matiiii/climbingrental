package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import java.util.List;

public interface CartService {
    void addProductToCart(ProductEntity product, int quantity);

    List<ProductEntity> getListOfProductsInCart();

    void removeProductFromCart();

    public Cart getCart();
}
