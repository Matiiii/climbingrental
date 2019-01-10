package pl.onsight.wypozyczalnia.model;

import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
public class Cart {

    List<ProductEntity> products = new LinkedList<>();

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public int getCountOfProductInCart(ProductEntity product){
        return Collections.frequency(products, product);
    }
}
