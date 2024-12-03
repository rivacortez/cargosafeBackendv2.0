package com.dynoware.cargosafe.platform.requestService.infrastructure.persistence.jpa.repositories;

import com.dynoware.cargosafe.platform.requestService.domain.model.aggregates.RequestService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestServiceRepository extends JpaRepository<RequestService, Long> {
    List<RequestService> findByUserId(Long userId);
}