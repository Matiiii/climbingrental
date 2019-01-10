package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.service.CartService;

import java.util.LinkedList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private Cart cart;

    @Autowired
    public CartServiceImpl(Cart cart) {
        this.cart = cart;
    }

    @Override
    public void addProductToCart(ProductEntity product, int quantity) {
        List<ProductEntity> products = cart.getProducts();
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    @Override
    public List<ProductEntity> getListOfProductsInCart() {
        return cart.getProducts();
    }

    @Override
    public void removeProductFromCart() {
        cart.setProducts(new LinkedList<>());
    }
}
