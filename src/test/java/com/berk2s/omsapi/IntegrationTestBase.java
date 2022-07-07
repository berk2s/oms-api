package com.berk2s.omsapi;

import com.berk2s.omsapi.domain.order.model.OrderAddress;
import com.berk2s.omsapi.domain.order.model.OrderLine;
import com.berk2s.omsapi.infra.OmsApiApplication;
import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.customer.repository.CustomerRepository;
import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import com.berk2s.omsapi.infra.adapters.inventory.repository.InventoryRepository;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderAddressEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderLineEntity;
import com.berk2s.omsapi.infra.adapters.order.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@ActiveProfiles("test")
@SpringBootTest(classes = {OmsApiApplication.class})
@AutoConfigureMockMvc
public class IntegrationTestBase {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public InventoryEntity createInventoryEntity(String barcode) {
        var inventory = new InventoryEntity();
        inventory.setBarcode(barcode != null ? barcode : RandomStringUtils.randomAlphabetic(10));
        inventory.setDescription(RandomStringUtils.randomAlphabetic(10));
        inventory.setPrice(BigDecimal.valueOf(10));
        inventory.setTotalQuantity(10);

        return inventoryRepository.save(inventory);
    }

    @Transactional
    public CustomerEntity createCustomer() {
        var customer = new CustomerEntity();
        customer.setFullName(RandomStringUtils.randomAlphabetic(10));

        return customerRepository.save(customer);
    }

    public OrderEntity createOrder(CustomerEntity customer) {
        var orderLine = createOrderLine();
        var order = new OrderEntity();
        order.setCustomer(customer);
        order.setPrice(orderLine.getPrice());
        order.setOrderAddress(createOrderAddress());
        order.addOrderLine(orderLine);

        return orderRepository.save(order);
    }

    public OrderLineEntity createOrderLine() {
        var barcode = RandomStringUtils.randomAlphabetic(10);
        var orderLine = new OrderLineEntity();
        orderLine.setBarcode(barcode);
        orderLine.setDescription(RandomStringUtils.randomAlphabetic(10));
        orderLine.setPrice(BigDecimal.valueOf(10));
        orderLine.setQuantity(10);
        orderLine.setProduct(createInventoryEntity(barcode));

        return orderLine;
    }

    public OrderAddressEntity createOrderAddress() {
        var orderAddress = new OrderAddressEntity();
        orderAddress.setPostalCode(35290);
        orderAddress.setCity("Izmir");
        orderAddress.setDistrict("Izmir");
        orderAddress.setPhoneNumber("5553332211");
        orderAddress.setCountryCode("TR");

        return orderAddress;
    }
}
