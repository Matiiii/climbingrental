package pl.sda.javapoz.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class FilterProducts {

    private Date orderStart;
    private Date orderEnd;

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

    public FilterProducts() {
    }

    public FilterProducts(Date orderStart, Date orderEnd) {
        this.orderStart = orderStart;
        this.orderEnd = orderEnd;
    }


}
