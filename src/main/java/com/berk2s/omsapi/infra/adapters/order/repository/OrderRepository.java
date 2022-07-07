package com.berk2s.omsapi.infra.adapters.order.repository;

import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import com.berk2s.omsapi.infra.adapters.order.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface OrderRepository extends PagingAndSortingRepository<OrderEntity, UUID> {

    Page<OrderEntity> findAllByCustomer(CustomerEntity customer, Pageable pageable);
}
