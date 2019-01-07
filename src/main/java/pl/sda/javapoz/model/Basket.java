package pl.sda.javapoz.model;

import org.springframework.stereotype.Component;
import pl.sda.javapoz.model.entity.ProductEntity;

import java.util.LinkedList;
import java.util.List;

@Component
public class Basket {

    List<ProductEntity> products = new LinkedList<>();

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}
