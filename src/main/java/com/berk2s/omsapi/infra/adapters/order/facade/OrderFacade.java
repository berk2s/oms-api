package com.berk2s.omsapi.infra.adapters.order.facade;

import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import com.berk2s.omsapi.infra.adapters.order.repository.OrderRepository;
import com.berk2s.omsapi.infra.exception.EntityNotFound;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderFacade {

    private final OrderRepository orderRepository;

    public OrderEntity findByOrderId(UUID orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> {
                    log.warn("Order with given id does not exists [orderId: {}]", orderId);
                    throw new EntityNotFound("order.notFound");
                });
    }

    public OrderEntity save(OrderEntity order) {
        return orderRepository.save(order);
    }

    public void delete(OrderEntity order) {
        orderRepository.delete(order);
    }

    public boolean existsById(UUID id) {
        return orderRepository
                .existsById(id);
    }
}
