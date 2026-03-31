package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.model.QuantityDTO;

public interface IQuantityMeasurementService {

	boolean compare(Long userId, QuantityDTO q1, QuantityDTO q2);

	QuantityDTO convert(Long userId, QuantityDTO quantity, QuantityDTO.IMeasurableUnit targetUnit);

	QuantityDTO add(Long userId, QuantityDTO q1, QuantityDTO q2);

	QuantityDTO add(Long userId, QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit);

	QuantityDTO subtract(Long userId, QuantityDTO q1, QuantityDTO q2);

	QuantityDTO subtract(Long userId, QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit);

	double divide(Long userId, QuantityDTO q1, QuantityDTO q2);
}