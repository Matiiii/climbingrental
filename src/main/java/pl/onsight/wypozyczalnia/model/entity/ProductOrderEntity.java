package pl.onsight.wypozyczalnia.model.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


@Entity(name = "product_order")
public class ProductOrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    private UserEntity user;

    @ManyToMany
    @Column(name = "product_id")
    private List<ProductEntity> products = new LinkedList<>();

    private Date orderStart;
    private Date orderEnd;
    private Double combinedPrice;
    private boolean paid;
    private String status;

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
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

    public ProductOrderEntity(UserEntity user, Date orderStart, Date orderEnd) {
        this.user = user;
        this.orderStart = orderStart;
        this.orderEnd = orderEnd;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public ProductOrderEntity(List<ProductEntity> products) {
        this.products = products;
    }
}
