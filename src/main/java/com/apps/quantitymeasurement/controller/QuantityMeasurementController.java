package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

public class QuantityMeasurementController {

	private final IQuantityMeasurementService service;

	public QuantityMeasurementController(IQuantityMeasurementService service) {
		this.service= service;
	}

	public boolean performComparison(QuantityDTO q1, QuantityDTO q2) {

		boolean result= service.compare(q1, q2);

		System.out.println("Comparing "+ q1 +" and "+ q2 +" : "+ result);

		return result;
	}

	public QuantityDTO performConversion(QuantityDTO quantity, QuantityDTO.IMeasurableUnit targetUnit) {

		QuantityDTO result= service.convert(quantity, targetUnit);

		System.out.println("Converted: "+ result);

		return result;
	}

	public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2) {

		QuantityDTO result= service.add(q1, q2);

		System.out.println("Addition result: "+ result);

		return result;
	}

	public QuantityDTO performAddition(QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit) {

		QuantityDTO result= service.add(q1, q2, targetUnit);

		System.out.println("Addition result: "+ result);

		return result;
	}

	public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2) {

		QuantityDTO result= service.subtract(q1, q2);

		System.out.println("Subtraction result: "+ result);

		return result;
	}

	public QuantityDTO performSubtraction(QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit) {

		QuantityDTO result= service.subtract(q1, q2, targetUnit);

		System.out.println("Subtraction result: "+ result);

		return result;
	}

	public double performDivision(QuantityDTO q1, QuantityDTO q2) {

		double result= service.divide(q1, q2);

		System.out.println("Division result: "+ result);

		return result;
	}
}