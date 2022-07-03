package com.berk2s.omsapi.domain.order.model;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.order.exception.EmptyProductState;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

@Slf4j
@Getter
public class Order {

    private Customer customer;
    private List<OrderLine> products;

    @Getter(AccessLevel.NONE)
    private OrderMoney totalPrice;

    public Order(Customer customer, List<OrderLine> products) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = OrderMoney.of(BigDecimal.ZERO);
    }

    public static Order newOrder(Customer customer, List<OrderLine> product) {
        var order = new Order(customer, product);
        order.validate();
        order.calculateOrderPrice();

        return order;
    }

    public void validate() {
        checkNonNull(customer, products);

        if (products.isEmpty()) {
            log.warn("Given order line list is empty [customerId: {}]", customer.getCustomerId());
            throw new EmptyProductState("orderLine.empty");
        }
    }

    /**
     * Will be executed only in creation time of Order
     */
    public void calculateOrderPrice() {
        this.products.forEach(product -> totalPrice.plus(product.getPrice()));
    }

    public void addProduct(OrderLine product) {
        checkNonNull(product);

        if (!products.contains(product)) {
            this.products.add(product);
            totalPrice.plus(product.getPrice());
        }
    }

    public void removeProduct(OrderLine product) {
        checkNonNull(product);

        if (products.contains(product)) {
            this.products.remove(product);
            totalPrice.minus(product.getPrice());
        }
    }

    public BigDecimal totalPrice() {
        return totalPrice.price();
    }
}
