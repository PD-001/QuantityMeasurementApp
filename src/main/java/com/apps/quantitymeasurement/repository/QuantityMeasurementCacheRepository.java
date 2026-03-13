package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class QuantityMeasurementCacheRepository implements IQuantityMeasurementRepository {

	private static final Logger logger= Logger.getLogger(QuantityMeasurementCacheRepository.class.getName());
	private static QuantityMeasurementCacheRepository instance;

	private final List<QuantityMeasurementEntity> cache= new ArrayList<>();

	private QuantityMeasurementCacheRepository() {
		logger.info("QuantityMeasurementCacheRepository initialized (in-memory)");
	}

	public static synchronized QuantityMeasurementCacheRepository getInstance() {

		if (instance==null)
			instance= new QuantityMeasurementCacheRepository();

		return instance;
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {
		cache.add(entity);
		logger.fine("Entity saved to cache — total: "+ cache.size());
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {
		return new ArrayList<>(cache);
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByOperationType(String operationType) {
		return cache.stream()
			.filter(e -> operationType.equalsIgnoreCase(e.getOperationType()))
			.collect(Collectors.toList());
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByMeasurementType(String measurementType) {
		return cache.stream()
			.filter(e -> measurementType.equalsIgnoreCase(e.getOperand1Unit()))
			.collect(Collectors.toList());
	}

	@Override
	public int getTotalCount() {
		return cache.size();
	}

	@Override
	public void deleteAllMeasurements() {
		cache.clear();
		logger.info("All measurements deleted from cache");
	}

	@Override
	public String getPoolStatistics() {
		return "CacheRepository [size="+ cache.size() +"]";
	}
}