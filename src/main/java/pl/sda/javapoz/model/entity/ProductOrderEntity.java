package pl.sda.javapoz.model.entity;

import javax.persistence.*;
import java.util.Date;


@Entity(name = "product_order")
public class ProductOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    private UserEntity userId;

    @OneToOne(cascade = CascadeType.PERSIST)
    private ProductEntity productId;

    private Date orderStart;
    private Date orderEnd;

    @Transient
    private Double combinedPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUserId() {
        return userId;
    }

    public void setUserId(UserEntity userId) {
        this.userId = userId;
    }

    public ProductEntity getProductId() {
        return productId;
    }

    public void setProductId(ProductEntity productId) {
        this.productId = productId;
    }

    public Date getOrderStart() {
        return orderStart;
    }

    public void setOrderStart(Date orderStart) {
        this.orderStart = orderStart;
    }

    public Date getOrderEnd() {
        return orderEnd;
    }

    public void setOrderEnd(Date orderEnd) {
        this.orderEnd = orderEnd;
    }

    public Double getCombinedPrice() {
        return combinedPrice;
    }

    public void setCombinedPrice(Double combinedPrice) {
        this.combinedPrice = combinedPrice;
    }

    public ProductOrderEntity() {
    }

    public ProductOrderEntity(UserEntity userId, ProductEntity productId, Date orderStart, Date orderEnd) {
        this.userId = userId;
        this.productId = productId;
        this.orderStart = orderStart;
        this.orderEnd = orderEnd;
    }
}
