package com.apps.quantitymeasurement.unit;

public interface IMeasurable {

	@FunctionalInterface
	interface SupportsArithmetic {
		boolean isSupported();
	}

	SupportsArithmetic supportsArithmetic= ()->true;

	double getConversionFactor();

	double convertToBaseUnit(double value);

	double convertFromBaseUnit(double baseValue);

	String getUnitName();

	String getMeasurementType();

	static IMeasurable getUnitByName(String unitName) {

		if (unitName==null) throw new IllegalArgumentException("Unit name cannot be null");

		String upper= unitName.toUpperCase();

		for (LengthUnit u:LengthUnit.values())
			if (u.name().equals(upper)) return u;

		for (WeightUnit u:WeightUnit.values())
			if (u.name().equals(upper)) return u;

		for (VolumeUnit u:VolumeUnit.values())
			if (u.name().equals(upper)) return u;

		for (TemperatureUnit u:TemperatureUnit.values())
			if (u.name().equals(upper)) return u;

		throw new IllegalArgumentException("Unknown unit: "+ unitName);
	}

	default boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	default void validateOperationSupport(String operation) {}
}