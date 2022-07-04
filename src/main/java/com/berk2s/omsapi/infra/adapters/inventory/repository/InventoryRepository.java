package com.berk2s.omsapi.infra.adapters.inventory.repository;

import com.berk2s.omsapi.infra.adapters.inventory.entity.InventoryEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;
import java.util.UUID;

public interface InventoryRepository extends PagingAndSortingRepository<InventoryEntity, UUID> {

    Optional<InventoryEntity> findByBarcode(String barcode);

    boolean existsByBarcode(String barcode);
}
