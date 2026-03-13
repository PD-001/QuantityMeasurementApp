package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementRepository {

	void save(QuantityMeasurementEntity entity);

	List<QuantityMeasurementEntity> getAllMeasurements();

	List<QuantityMeasurementEntity> getMeasurementsByOperationType(String operationType);

	List<QuantityMeasurementEntity> getMeasurementsByMeasurementType(String measurementType);

	int getTotalCount();

	void deleteAllMeasurements();

	default String getPoolStatistics() {
		return "Pool statistics not available for this repository";
	}

	default void releaseResources() {}
}