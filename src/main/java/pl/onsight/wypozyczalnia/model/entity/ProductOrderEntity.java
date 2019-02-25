package pl.onsight.wypozyczalnia.model.entity;

import org.joda.time.DateTime;
import org.joda.time.Days;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.Cart;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
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
    private HashMap<Long, Double> oldPrices = new HashMap<>();

    private Date orderStart;
    private Date orderEnd;
    private Double combinedPrice;
    private Double deposit;
    private boolean paid;
    private String status;
    private Double combinedDiscount;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
    private void createOldPricesHashMap(Cart cart) {
        for (ProductEntity product : cart.getProducts()) {
            oldPrices.put(product.getId(), product.getPrice());
        }
    }

    public void buildOrder(UserEntity user, Cart cart) {
        ProductOrderEntity order = new ProductOrderEntity();

        order.setUser(user);
        order.setProducts(cart.getProducts());
        order.setOrderStart(DateFilter.changeStringToDate(cart.getDate())[0]);
        order.setOrderEnd(DateFilter.changeStringToDate(cart.getDate())[1]);
        order.setCombinedPrice(cart.getPriceWithDiscount(user));
        order.setDeposit(cart.getCombinedDeposit());
        order.setCombinedDiscount(user.getRole().getDiscount());
        order.createOldPricesHashMap(cart);

    }
}
