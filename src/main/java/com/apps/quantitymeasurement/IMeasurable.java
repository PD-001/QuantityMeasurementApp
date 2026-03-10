package com.apps.quantitymeasurement;

public interface IMeasurable {

	@FunctionalInterface
	interface SupportsArithmetic {
		boolean isSupported();
	}

	SupportsArithmetic supportsArithmetic= () -> true;

	double getConversionFactor();

	double convertToBaseUnit(double value);

	double convertFromBaseUnit(double baseValue);

	String getUnitName();

	default boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	default void validateOperationSupport(String operation) {
	}
}