package pl.onsight.wypozyczalnia.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.model.CountProducts;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.validator.OrderValidator;

import java.util.List;

@Component
public class OrderValidatorImpl implements OrderValidator {

    private ProductService productService;

    @Autowired
    public OrderValidatorImpl(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public boolean isOrderCorrectToSave(ProductOrderEntity order) {
        return hasOrderProductsInCart(order) && isOrderAvailableToSave(order);
    }
    private boolean isOrderAvailableToSave(ProductOrderEntity order){
        List<ProductEntity> products = order.getProducts();
        List<CountProducts> countProducts = productService.changeProductEntityListToCountProductsList(products);

        for (CountProducts countProduct : countProducts) {
            if (productService.countProductsAvailableByNameAndTime(
                    countProduct.getProduct().getProductName(), order.getOrderStart(), order.getOrderEnd())
                    < countProduct.getCount()) {
                return false;
            }
        }

        return true;
    }

    private boolean hasOrderProductsInCart(ProductOrderEntity order) {
        return order.getProducts().size()>0;
    }
}
