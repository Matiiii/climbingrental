package pl.onsight.wypozyczalnia.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.onsight.wypozyczalnia.DateFilter;
import pl.onsight.wypozyczalnia.model.entity.ProductEntity;
import pl.onsight.wypozyczalnia.model.entity.ProductOrderEntity;
import pl.onsight.wypozyczalnia.repository.ProductOrderRepository;
import pl.onsight.wypozyczalnia.repository.ProductRepository;
import pl.onsight.wypozyczalnia.service.ProductOrderService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {

    private ProductOrderRepository productOrderRepository;
    private ProductRepository productRepository;

    @Autowired
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository, ProductRepository productRepository) {
        this.productOrderRepository = productOrderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void saveOrder(ProductOrderEntity order) {
        productOrderRepository.save(order);
    }

    @Override
    public List<ProductOrderEntity> findProductsByUserId(Long id) {
        return productOrderRepository.findByUserId(id);
    }

    @Override
    public ProductOrderEntity getPriceOfOrderedProduct(ProductOrderEntity productOrder) {
        //Double price = productOrder.getProductId().getPrice();
        Long productOrderStartTime = productOrder.getOrderStart().getTime();
        Long productOrderEndTime = productOrder.getOrderEnd().getTime();
        Long numberOfMillisecondsInDay = (long) (1000 * 60 * 60 * 24);
        Double lengthOfOrder = (double) (productOrderEndTime - productOrderStartTime + numberOfMillisecondsInDay) / numberOfMillisecondsInDay;
        //productOrder.setCombinedPrice(price * lengthOfOrder);
        return productOrder;
    }

    @Override
    public Double getPriceOfOrderedProducts(List<ProductOrderEntity> productOrders) {
        Double[] sum = {0.0};
        // productOrders.forEach(product -> sum[0] += product.getCombinedPrice());

        return sum[0];
    }

    @Override
    public Integer countOrdersProductInPeriod(Long productId, Date productOrderStart, Date productOrderEnd) {
        List<ProductOrderEntity> orders = findProductOrdersByProductId(productId);
        int count = 0;
        for (ProductOrderEntity order : orders) {
            Date orderStart = order.getOrderStart();
            Date orderEnd = order.getOrderEnd();
            if (productOrderStart.before(orderEnd) && productOrderEnd.after(orderStart)) {
                count += order.getProducts().stream()
                        .filter(productEntity -> productEntity.getId().equals(productId))
                        .collect(Collectors.toList())
                        .size();
            }
        }

        return count;
    }

    @Override
    public boolean isProductAvailableToOrder(Long productId, String dateFilter) throws ParseException {
        Date[] dates = DateFilter.changeStringToDate(dateFilter);
        Date productOrderStart = dates[0];
        Date productOrderEnd = dates[1];

        return isProductAvailableToOrder(productId, productOrderStart, productOrderEnd);
    }

    @Override
    public boolean isProductAvailableToOrder(Long productId, Date productOrderStart, Date productOrderEnd) {
        ProductEntity product = productRepository.findOne(productId);
        Integer countOrders = countOrdersProductInPeriod(productId, productOrderStart, productOrderEnd);

        return countOrders < product.getQuantity();
    }

    @Override
    public List<String> getListOfDatesWhenProductIsReserved(Long id) {
        List<String> dates = new ArrayList<>();
        List<ProductOrderEntity> orders = findProductOrdersByProductId(id);

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

    private List<ProductOrderEntity> findProductOrdersByProductId(Long productId) {
        ProductEntity product = productRepository.findOne(productId);
        return productOrderRepository.findAllByProductsContaining(product);
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