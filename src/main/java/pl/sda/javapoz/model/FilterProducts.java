package pl.sda.javapoz.model;

import java.util.Date;

/**
 * Created by RENT on 2017-03-22.
 */

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
