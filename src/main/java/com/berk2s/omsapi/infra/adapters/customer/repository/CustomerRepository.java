package com.berk2s.omsapi.infra.adapters.customer.repository;

import com.berk2s.omsapi.infra.adapters.customer.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {
}
