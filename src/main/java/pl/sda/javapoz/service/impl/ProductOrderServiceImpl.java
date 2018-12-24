package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.Product;
import pl.sda.javapoz.model.ProductOrder;
import pl.sda.javapoz.model.User;
import pl.sda.javapoz.repository.ProductOrderRepository;
import pl.sda.javapoz.service.ProductOrderService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private ProductOrderRepository productOrderRepository;

    @Autowired
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }

    @Override
    public void saveOrder(User userId, Product productId, Date orderStart, Date orderEnd) {
        productOrderRepository.save(new ProductOrder(userId, productId, orderStart, orderEnd));
    }

    @Override
    public List<ProductOrder> findProductsByUserId(Long id) {
        return productOrderRepository.findByUserIdId(id);
    }

    @Override
    public ProductOrder getPriceOfOrderedProduct(ProductOrder productOrder) {
        Double price = productOrder.getProductId().getPrice();
        Long productOrderStartTime = productOrder.getOrderStart().getTime();
        Long productOrderEndTime = productOrder.getOrderEnd().getTime();
        Double lengthOfOrder = (double) (productOrderEndTime - productOrderStartTime) / (1000 * 60 * 60 * 24);
        productOrder.setCombinedPrice(price * lengthOfOrder);
        return productOrder;
    }

    @Override
    public Double getPriceOfOrderedProducts(List<ProductOrder> productOrders) {
        Double sum = 0.0;
        for (ProductOrder productOrder : productOrders) {
            sum += getPriceOfOrderedProduct(productOrder).getCombinedPrice();
        }
        return sum;
    }

    @Override
    public List<ProductOrder> findProductOrderByProductId(Long productId) {
        return productOrderRepository.findByProductIdId(productId);
    }

    @Override
    public boolean isProductAvailableToOrder(Long id, Date productOrderStart, Date productOrderEnd) {
        List<ProductOrder> orders = findProductOrderByProductId(id);
        boolean availableToOrder = true;
        for (ProductOrder order : orders) {
            Date orderStart = order.getOrderStart();
            Date orderEnd = order.getOrderEnd();
            if (productOrderStart.before(orderEnd) && productOrderEnd.after(orderStart)) {
                availableToOrder = false;
            }
        }
        return availableToOrder;
    }

    @Override
    public List<String> getListOfDatesWhenProductIsReserved(Long id) {
        List<String> dates = new ArrayList<>();
        List<ProductOrder> orders = findProductOrderByProductId(id);

        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for (ProductOrder order : orders) {
            Date orderStart = order.getOrderStart();
            Date orderEnd = order.getOrderEnd();
            dates.add(simpleDateFormat.format(orderStart));
            Calendar cal = Calendar.getInstance();
            cal.setTime(orderStart);
            while (cal.getTime().before(orderEnd)) {
                cal.add(Calendar.DATE, 1);
                dates.add(simpleDateFormat.format(cal.getTime()));
            }
        }
        return dates;
    }

    @Override
    public List<ProductOrder> findAllProductOrders() {
        List<ProductOrder> productOrders = new ArrayList<>();
        Iterable<ProductOrder> productOrderIterable = productOrderRepository.findAll();
        productOrderIterable.forEach(productOrders::add);

        return productOrders;
    }

    @Override
    public void removeProductOrderByAdmin(Long id) {
        productOrderRepository.delete(id);
    }
}