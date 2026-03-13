package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

import java.util.logging.Logger;

public class QuantityMeasurementController {

	private static final Logger logger= Logger.getLogger(QuantityMeasurementController.class.getName());

	private final IQuantityMeasurementService service;

	public QuantityMeasurementController(IQuantityMeasurementService service) {
		this.service= service;
		logger.info("QuantityMeasurementController initialized");
	}

	public boolean performComparison(QuantityDTO q1, QuantityDTO q2) {

		boolean result= service.compare(q1, q2);

		logger.info("Comparing "+ q1 +" and "+ q2 +" : "+ result);

		return result;
	}

	public QuantityDTO performConversion(QuantityDTO quantity, QuantityDTO.IMeasurableUnit targetUnit) {

		QuantityDTO result= service.convert(quantity, targetUnit);

		logger.info("Converted: "+ result);

		return result;
	}

	public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2) {

		QuantityDTO result= service.add(q1, q2);

		logger.info("Addition result: "+ result);

		return result;
	}

	public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit) {

		QuantityDTO result= service.add(q1, q2, targetUnit);

		logger.info("Addition result: "+ result);

		return result;
	}

	public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2) {

		QuantityDTO result= service.subtract(q1, q2);

		logger.info("Subtraction result: "+ result);

		return result;
	}

	public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit) {

		QuantityDTO result= service.subtract(q1, q2, targetUnit);

		logger.info("Subtraction result: "+ result);

		return result;
	}

	public double performDivision(QuantityDTO q1, QuantityDTO q2) {

		double result= service.divide(q1, q2);

		logger.info("Division result: "+ result);

		return result;
	}
}