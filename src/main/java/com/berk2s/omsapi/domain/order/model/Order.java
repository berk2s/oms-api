package com.berk2s.omsapi.domain.order.model;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.order.exception.EmptyProductState;
import com.berk2s.omsapi.domain.product.model.Product;
import lombok.AccessLevel;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

@Getter
public class Order {

    private Customer customer;
    private List<Product> products;

    @Getter(AccessLevel.NONE)
    private OrderMoney totalPrice;

    public Order(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
        this.totalPrice = OrderMoney.of(BigDecimal.ZERO);
    }

    public static Order newOrder(Customer customer, List<Product> product) {
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
        this.products.forEach(product -> totalPrice.plus(product.getPrice()));
    }

    public void addProduct(Product product) {
        checkNonNull(product);

        if (!products.contains(product)) {
            this.products.add(product);
            totalPrice.plus(product.getPrice());
        }
    }

    public void removeProduct(Product product) {
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
