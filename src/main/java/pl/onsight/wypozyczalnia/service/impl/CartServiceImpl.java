package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.service.CartService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@SessionAttributes("cart")
public class CartServiceImpl implements CartService {

    @Override
    public void addProductToCart(Cart cart, ProductEntity product, int quantity) {
        List<ProductEntity> products = cart.getProducts();
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    @Override
    public void addDateToCart(Cart cart, String dateFilter) {
        if(dateFilter != null && dateFilter.length() > 0){
            cart.setDate(dateFilter);
        }
    }

    @Override
    public void addDiscountToCart(Cart cart, double discount) {
        cart.setDiscount(discount);
    }

    @Override
    public List<ProductEntity> getListOfProductsInCart(Cart cart) {
        return cart.getProducts();
    }

    @Override
    public void removeProductsFromCart(Cart cart) {
        cart.setProducts(new LinkedList<>());
    }
}
