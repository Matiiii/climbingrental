package pl.onsight.wypozyczalnia.model;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.stereotype.Component;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Component
public class Cart {

    private List<ProductEntity> products = new LinkedList<>();
    private String date;
    private double discount = 0;

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public int getNumberOfDays(){
        Date[] dates = null;
        if(date != null){
            dates = DateFilter.changeStringToDate(date);
        }

        return (date != null) ? Days.daysBetween(new DateTime(dates[0]), new DateTime(dates[1])).getDays() + 1 : 0;
    }

    public double getCombinedPrice(){
        double[] combinedPrice = {0};
        products.forEach(product -> combinedPrice[0] += product.getPrice());

        return combinedPrice[0] * getNumberOfDays();
    }

    public double getCombinedDeposit(){
        double[] combinedDeposit = {0};
        products.forEach(product -> combinedDeposit[0] += product.getDeposit());

        return combinedDeposit[0];
    }

    public int getCountOfProductsInCart(ProductEntity product) {
        return Collections.frequency(products, product);
    }

    public double getPriceWithDiscount(){
        double discount = 1.0 - this.discount/100;
        double finalPrice = getCombinedPrice() * discount;
        finalPrice *= 100;
        finalPrice = Math.round(finalPrice);
        finalPrice /= 100;
        return finalPrice;
    }


}


