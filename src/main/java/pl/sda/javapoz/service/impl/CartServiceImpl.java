package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.Cart;
import pl.sda.javapoz.model.entity.ProductEntity;
import pl.sda.javapoz.repository.ProductRepository;
import pl.sda.javapoz.service.CartService;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    private Cart cart;
    private ProductRepository productRepository;

    @Autowired
    public CartServiceImpl(Cart cart, ProductRepository productRepository) {
        this.cart = cart;
        this.productRepository = productRepository;
    }

    @Override
    public void addProductToCart(ProductEntity product, int quantity) {
        List<ProductEntity> products = cart.getProducts();
        String productName = product.getProductName();
        //List<ProductEntity> sameProducts = productRepository.findByProductName(productName);
/*        List<ProductEntity> sameProducts = productRepository.findByProductName(productName);

        for(int i = 0; i < quantity && i < sameProducts.size(); i++){
            products.add(sameProducts.get(i));
        }*/

        for(int i = 0; i < quantity ; i++){
            products.add(product);
        }
    }

    @Override
    public List<ProductEntity> getListOfProductsInCart() {
        return cart.getProducts();
    }
}
