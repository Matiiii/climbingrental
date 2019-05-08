package pl.onsight.wypozyczalnia.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.SessionAttributes;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.service.CartService;
import pl.onsight.wypozyczalnia.service.ProductService;

import java.util.LinkedList;
import java.util.List;

@Service
@SessionAttributes("cart")
public class CartServiceImpl implements CartService {

    private ProductService productService;

    @Autowired
    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public void addProductToCart(Cart cart, ProductEntity product, int quantity) {
        List<ProductEntity> products = cart.getProducts();
        for (int i = 0; i < quantity; i++) {
            products.add(product);
        }
    }

    @Override
    public void addDateToCart(Cart cart, String dateFilter) {
        if (dateFilter != null && dateFilter.length() > 0) {
            cart.setDate(dateFilter);
        }
    }

    @Override
    public List<ProductEntity> getListOfProductsInCart(Cart cart) {
        return cart.getProducts();
    }

    @Override
    public void removeProductsFromCart(Cart cart) {
        cart.setProducts(new LinkedList<>());
    }

    @Override
    public void removeProductFromCart(Cart cart, ProductEntity product) {
        cart.getProducts().remove(product);
    }

    @Override
    public void removeProductsOneTypeFromCart(Cart cart, ProductEntity product) {
        cart.getProducts().removeIf(productEntity -> productEntity.equals(product));
    }

    @Override
    public List<CountProducts> getCountedProductsInCartWithAvailable(Cart cart) {
        List<CountProducts> countedProducts = productService.changeProductEntityListToCountProductsList(cart.getProducts());
        if (cart.getDate() != null) {
            for (CountProducts countProduct : countedProducts) {
                countProduct.setCountAvailable(productService.countProductsAvailableByNameAndTime(
                        countProduct.getProduct().getProductName(), DateFilter.changeStringToDate(cart.getDate())[0],
                        DateFilter.changeStringToDate(cart.getDate())[1]));
            }
        } else {
            for (CountProducts countProduct : countedProducts) {
                countProduct.setCountAvailable(countProduct.getProduct().getQuantity());
            }
        }
        return countedProducts;
    }
}
