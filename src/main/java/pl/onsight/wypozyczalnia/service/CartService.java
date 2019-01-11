package pl.onsight.wypozyczalnia.service;

import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import java.util.List;

public interface CartService {
    void addProductToCart(Cart cart, ProductEntity product, int quantity);

    List<ProductEntity> getListOfProductsInCart(Cart cart);


    public Cart getCart();
    void removeProductFromCart(Cart cart);
}
