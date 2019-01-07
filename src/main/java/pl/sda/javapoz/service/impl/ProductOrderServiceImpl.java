package pl.sda.javapoz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.javapoz.model.entity.ProductEntity;
import pl.sda.javapoz.model.entity.ProductOrderEntity;
import pl.sda.javapoz.model.entity.UserEntity;
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
    public void saveOrder(UserEntity userId, ProductEntity productId, Date orderStart, Date orderEnd) {
        productOrderRepository.save(new ProductOrderEntity(userId, productId, orderStart, orderEnd));
    }

    @Override
    public List<ProductOrderEntity> findProductsByUserId(Long id) {
        return productOrderRepository.findByUserIdId(id);
    }

    @Override
    public ProductOrderEntity getPriceOfOrderedProduct(ProductOrderEntity productOrder) {
        Double price = productOrder.getProductId().getPrice();
        Long productOrderStartTime = productOrder.getOrderStart().getTime();
        Long productOrderEndTime = productOrder.getOrderEnd().getTime();
        Long numberOfMillisecondsInDay = (long) (1000 * 60 * 60 * 24);
        Double lengthOfOrder = (double) (productOrderEndTime - productOrderStartTime + numberOfMillisecondsInDay) / numberOfMillisecondsInDay;
        productOrder.setCombinedPrice(price * lengthOfOrder);
        return productOrder;
    }

    @Override
    public Double getPriceOfOrderedProducts(List<ProductOrderEntity> productOrders) {
        Double[] sum = {0.0};
        productOrders.forEach(product -> sum[0] += product.getCombinedPrice());

        return sum[0];
    }

    @Override
    public List<ProductOrderEntity> findProductOrderByProductId(Long productId) {
        return productOrderRepository.findByProductIdId(productId);
    }

    @Override
    public boolean isProductAvailableToOrder(Long id, Date productOrderStart, Date productOrderEnd) {
        List<ProductOrderEntity> orders = findProductOrderByProductId(id);
        boolean availableToOrder = true;
        for (ProductOrderEntity order : orders) {
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
        List<ProductOrderEntity> orders = findProductOrderByProductId(id);

        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        for (ProductOrderEntity order : orders) {
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
    public List<ProductOrderEntity> findAllProductOrders() {
        List<ProductOrderEntity> productOrders = new ArrayList<>();
        Iterable<ProductOrderEntity> productOrderIterable = productOrderRepository.findAll();
        productOrderIterable.forEach(productOrders::add);

        return productOrders;
    }

    @Override
    public void removeProductOrder(Long id) {
        productOrderRepository.delete(id);
    }
}