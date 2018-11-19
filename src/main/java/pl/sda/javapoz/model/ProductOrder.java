package pl.sda.javapoz.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by RENT on 2017-03-22.
 */
@Entity
public class ProductOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(cascade = CascadeType.PERSIST)
    //@Column(name = "USER_ID")
    private User userId;

    @OneToOne(cascade = CascadeType.PERSIST)
    //@Column(name = "PRODUCT_ID")
    private Product productId;

    //@Column(name = "order_start")
    private Date orderStart;

    //@Column(name = "order_end")
    private Date orderEnd;

    @Transient
    private Double combinedPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public Product getProductId() {
        return productId;
    }

    public void setProductId(Product productId) {
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

    public Double getCombinedPrice() { return combinedPrice;   }

    public void setCombinedPrice(Double combinedPrice) {   this.combinedPrice = combinedPrice;  }

    public ProductOrder() {
    }

    public ProductOrder(User userId, Product productId, Date orderStart, Date orderEnd) {
        this.userId = userId;
        this.productId = productId;
        this.orderStart = orderStart;
        this.orderEnd = orderEnd;
    }
}
