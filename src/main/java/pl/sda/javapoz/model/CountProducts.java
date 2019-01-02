package pl.sda.javapoz.model;

public class CountProducts {

    private ProductEntity product;
    private Integer count;

    public CountProducts() {
    }

    public CountProducts(ProductEntity product, Integer count) {
        this.product = product;
        this.count = count;
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
