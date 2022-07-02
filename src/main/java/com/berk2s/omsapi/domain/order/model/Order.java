package com.berk2s.omsapi.domain.order.model;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.order.exception.EmptyProductState;
import com.berk2s.omsapi.domain.product.model.Product;

import java.math.BigDecimal;
import java.util.List;

import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

public class Order {

    private Customer customer;
    private List<Product> products;
    private OrderMoney money;

    public Order(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
        this.money = OrderMoney.of(BigDecimal.ZERO);
    }

    public static Order create(Customer customer, List<Product> product) {
        var order = new Order(customer, product);
        order.validate();
        order.calculateOrderPrice();

        return order;
    }

    public void validate() {
        checkNonNull(customer, products);

        if (products.isEmpty()) {
            throw new EmptyProductState("Products belong to order can not be empty");
        }
    }

    /**
     * Will be executed only in creation time of Order
     */
    public void calculateOrderPrice() {
        this.products.forEach(product -> money.plus(product.getPrice()));
    }

    public void addProduct(Product product) {
        checkNonNull(product);

        if (!products.contains(product)) {
            this.products.add(product);
            money.plus(product.getPrice());
        }
    }

    public void removeProduct(Product product) {
        checkNonNull(product);

        if (products.contains(product)) {
            this.products.remove(product);
            money.minus(product.getPrice());
        }
    }

    public BigDecimal totalPrice() {
        return money.price();
    }
}
