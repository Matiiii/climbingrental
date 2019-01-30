package pl.onsight.wypozyczalnia.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.service.ProductService;
import pl.onsight.wypozyczalnia.validator.ProductValidator;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductValidatorImpl implements ProductValidator {

    ProductService productService;

    @Autowired
    public ProductValidatorImpl(ProductService productService) {
        this.productService = productService;
    }


    @Override
    public boolean isNameUsed(ProductEntity product) {
        List<String> allNamesOfProducts = productService.findAllProducts().stream().map(productEntity -> productEntity.getProductName()).collect(Collectors.toList());
        String tempName = productService.findProductById(product.getId()).getProductName();
        allNamesOfProducts.remove(tempName);
        return allNamesOfProducts.contains(product.getProductName());
    }
}
