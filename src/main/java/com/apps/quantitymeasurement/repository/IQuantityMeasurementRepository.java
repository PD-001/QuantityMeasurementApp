package com.apps.quantitymeasurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;

import java.util.List;

@Repository
public interface IQuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    List<QuantityMeasurementEntity> findAllByOrderByCreatedAtDesc();

    List<QuantityMeasurementEntity> findByOperationTypeIgnoreCaseOrderByCreatedAtDesc(
            String operationType);

    List<QuantityMeasurementEntity> findByOperand1UnitIgnoreCaseOrderByCreatedAtDesc(
            String measurementUnit);
    
    List<QuantityMeasurementEntity> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<QuantityMeasurementEntity> findByUserIdAndOperationTypeIgnoreCaseOrderByCreatedAtDesc(
            Long userId, String operationType);

    void deleteByUserId(Long userId);

    long countByUserId(Long userId);

}