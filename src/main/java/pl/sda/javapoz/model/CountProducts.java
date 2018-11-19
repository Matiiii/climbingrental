package pl.sda.javapoz.model;

/**
 * Created by RENT on 2017-03-28.
 */
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
