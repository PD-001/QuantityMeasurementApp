package com.apps.quantitymeasurement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;

import java.util.List;

@Repository
public interface IQuantityMeasurementRepository
        extends JpaRepository<QuantityMeasurementEntity, Long> {

    // Spring Data generates the SQL for these automatically from the method name:

    List<QuantityMeasurementEntity> findAllByOrderByCreatedAtDesc();

    List<QuantityMeasurementEntity> findByOperationTypeIgnoreCaseOrderByCreatedAtDesc(
            String operationType);

    List<QuantityMeasurementEntity> findByOperand1UnitIgnoreCaseOrderByCreatedAtDesc(
            String measurementUnit);

}