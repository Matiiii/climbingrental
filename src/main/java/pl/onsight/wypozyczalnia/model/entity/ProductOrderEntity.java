package pl.onsight.wypozyczalnia.model.entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.Days;
import pl.onsight.wypozyczalnia.model.Cart;
import pl.onsight.wypozyczalnia.model.enums.Status;

import javax.persistence.*;
import java.util.*;


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

    @Type(
            type = "org.hibernate.type.SerializableToBlobType",
            parameters = {@org.hibernate.annotations.Parameter(name = "classname", value = "java.util.HashMap")}
    )

    private HashMap<Long, Double> oldPrices = new HashMap<>();

    private Date orderStart;
    private Date orderEnd;
    private Double combinedPrice;
    private Double deposit;
    private boolean paid;
    private Double combinedDiscount;
    private Status statusOfOrder;

    public Status getStatusOfOrder() {
        return statusOfOrder;
    }

    public void setStatusOfOrder(Status statusOfOrder) {
        this.statusOfOrder = statusOfOrder;
    }

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

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getCombinedDiscount() {
        return combinedDiscount;
    }

    public void setCombinedDiscount(Double combinedDiscount) {
        this.combinedDiscount = combinedDiscount;
    }

    public HashMap<Long, Double> getOldPrices() {
        return oldPrices;
    }

    public void setOldPrices(HashMap<Long, Double> oldPrices) {
        this.oldPrices = oldPrices;
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

    public int getNumberOfDays() {
        return (orderStart != null && orderEnd != null) ? Days.daysBetween(new DateTime(orderStart), new DateTime(orderEnd)).getDays() + 1 : 0;
    }

    public void createOldPricesHashMap(Cart cart) {
        for (ProductEntity product : cart.getProducts()) {
            oldPrices.put(product.getId(), product.getPrice());
        }
    }

    public int countNumberOfProduct(ProductEntity product) {
        return Collections.frequency(products, product);

    }

    @Override
    public String toString() {
        return "ProductOrderEntity{" +
                "id=" + id +
                ", user=" + user +
                ", products=" + products +
                ", oldPrices=" + oldPrices +
                ", orderStart=" + orderStart +
                ", orderEnd=" + orderEnd +
                ", combinedPrice=" + combinedPrice +
                ", deposit=" + deposit +
                ", paid=" + paid +
                ", combinedDiscount=" + combinedDiscount +
                ", statusOfOrder=" + statusOfOrder +
                '}';
    }
}
