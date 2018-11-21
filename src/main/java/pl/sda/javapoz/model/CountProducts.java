package pl.sda.javapoz.model;

import javax.persistence.Entity;

@Entity
public class CountProducts {

    private Product product;
    private Integer count;

    public CountProducts() {
    }

    public CountProducts(Product product, Integer count) {
        this.product = product;
        this.count = count;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
