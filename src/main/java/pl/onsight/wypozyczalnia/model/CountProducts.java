package pl.onsight.wypozyczalnia.model;

import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

public class CountProducts {

    private ProductEntity product;
    private Integer count;

    public Integer getCountAvailable() {
        return countAvailable;
    }

    public void setCountAvailable(Integer countAvailable) {
        this.countAvailable = countAvailable;
    }

    private Integer countAvailable;

    public CountProducts() {
    }

    public CountProducts(ProductEntity product, Integer count) {
        this.product = product;
        this.count = count;
    }
    public CountProducts(ProductEntity product, Integer count, Integer countAvailable ) {
        this.product = product;
        this.count = count;
        this.countAvailable = countAvailable;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
