package com.apps.quantitymeasurement.model;

import com.apps.quantitymeasurement.quantity.Quantity;
import com.apps.quantitymeasurement.unit.IMeasurable;

public class QuantityModel<U extends IMeasurable> {

	private final double value;
	private final U unit;

	public QuantityModel(double value, U unit) {

		if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");

		if (unit==null) throw new IllegalArgumentException("Unit cannot be null");

		this.value= value;
		this.unit= unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	public Quantity<U> toQuantity() {
		return new Quantity<>(value, unit);
	}

	@Override
	public String toString() {
		return value +":"+ unit.getUnitName();
	}
}