package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.Basket;
import pl.sda.javapoz.model.entity.ProductEntity;
import pl.sda.javapoz.service.BasketService;

import java.util.List;

@Service
public class BasketServiceImpl implements BasketService {

    private Basket basket;

    @Autowired
    public BasketServiceImpl(Basket basket) {
        this.basket = basket;
    }

    @Override
    public void addProductToBasket(ProductEntity product) {
        List<ProductEntity> products = basket.getProducts();
        products.add(product);
        for(ProductEntity p: products){
            System.out.println(p.getProductName());
        }
    }
}
