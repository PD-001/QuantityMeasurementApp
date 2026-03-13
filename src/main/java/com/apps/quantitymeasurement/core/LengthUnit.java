package com.apps.quantitymeasurement.core;

public enum LengthUnit implements IMeasurable {

	INCHES(1.0),
	FEET(12.0),
	YARDS(36.0),
	CENTIMETERS(0.393701);

	private final double conversionFactor;

	LengthUnit(double conversionFactor) {
		this.conversionFactor= conversionFactor;
	}

	@Override
	public double getConversionFactor() {
		return conversionFactor;
	}

	@Override
	public double convertToBaseUnit(double value) {
		return value*conversionFactor;
	}

	@Override
	public double convertFromBaseUnit(double baseValue) {
		return baseValue/conversionFactor;
	}

	@Override
	public String getUnitName() {
		return this.name();
	}

	@Override
	public String getMeasurementType() {
		return "LENGTH";
	}
}