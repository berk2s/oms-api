package com.berk2s.omsapi.infra.adapters.order.repository;

import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, UUID> {
}
