package com.berk2s.omsapi.domain.order.model;

import com.berk2s.omsapi.domain.customer.model.Customer;
import com.berk2s.omsapi.domain.order.exception.EmptyProductState;
import com.berk2s.omsapi.domain.order.exception.ProductNotFound;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.berk2s.omsapi.domain.validation.NullValidator.checkNonNull;

@Slf4j
@Getter
@Setter // only for testing purposes
public class Order {

    private UUID orderId;
    private Customer customer;
    private List<OrderLine> products;
    private OrderAddress address;

    @Getter(AccessLevel.NONE)
    private OrderMoney totalPrice;

    public Order() {}

    public Order(UUID orderId, Customer customer, OrderAddress address, List<OrderLine> products) {
        this.orderId = orderId;
        this.customer = customer;
        this.products = products;
        this.address = address;
        this.totalPrice = OrderMoney.of(BigDecimal.ZERO);
    }

    public static Order newOrder(Customer customer, OrderAddress address, List<OrderLine> products) {
        var order = new Order(null, customer, address, products);
        order.validate();
        order.calculateOrderPrice();

        return order;
    }

    public static Order newOrder(UUID orderId, Customer customer, OrderAddress address, List<OrderLine> products) {
        var order = new Order(orderId, customer, address, products);
        order.validate();
        order.calculateOrderPrice();

        return order;
    }

    public static Order empty() {
        return new Order();
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
            log.warn("Product added to Order [orderId: {}, product: {}]", orderId, product);
        }
    }

    public void removeProduct(OrderLine product) {
        checkNonNull(product);

        if (products.contains(product)) {
            this.products.remove(product);
            totalPrice.minus(product.getPrice());
            log.warn("Product deleted from Order [orderId: {}, product: {}]", orderId, product);
        }
    }

    /**
     * Updating process is performing by immutable way
     * So when update a product, firstly deletes product from list
     * Then, adds same product with updated fields
     */
    public void updateProduct(OrderLine product) {
        checkNonNull(product);

        var productOpt = products.stream()
                .filter(i -> i.equals(product))
                .findFirst();

        productOpt.ifPresent(this::removeProduct);

        addProduct(product);

        log.info("Product updated in the Order [orderId: {}, updatedProduct: {}]", orderId, product);
    }

    public void updateAddress(OrderAddress address) {
        this.address = address;
        log.info("Order address updated [orderId: {}, updatedAddress: {}]", orderId,
                address);
    }

    public BigDecimal totalPrice() {
        return totalPrice.price();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return orderId.equals(order.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId);
    }
}
