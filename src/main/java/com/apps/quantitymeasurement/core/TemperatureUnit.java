package com.apps.quantitymeasurement.core;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

	CELSIUS(value-> value, baseValue-> baseValue),
	FAHRENHEIT(value-> (value-32.0)*(5.0/9.0), baseValue-> (baseValue*(9.0/5.0))+32.0);

	private final Function<Double, Double> toCelsius;
	private final Function<Double, Double> fromCelsius;
	
	SupportsArithmetic supportsArithmetic= ()-> false;

	TemperatureUnit(Function<Double, Double> toCelsius, Function<Double, Double> fromCelsius) {
		this.toCelsius= toCelsius;
		this.fromCelsius= fromCelsius;
	}

	@Override
	public double getConversionFactor() {
		return 1.0;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return toCelsius.apply(value);
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return fromCelsius.apply(baseValue);
	}

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
		return "TEMPERATURE";
	}

	@Override
	public boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	@Override
	public void validateOperationSupport(String operation) {
		throw new UnsupportedOperationException(
			"Operation "+ operation +" is not supported. Temperature measurements only support equality comparison and unit conversion."
		);
	}
}