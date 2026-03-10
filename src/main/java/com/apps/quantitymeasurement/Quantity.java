package com.apps.quantitymeasurement;

public class Quantity<U extends IMeasurable> {

	private final double value;
	private final U unit;

	private static final double epsilon= 0.0001;

	public Quantity(double value, U unit) {

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

	private double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}

	public Quantity<U> convertTo(U targetUnit) {

		if (targetUnit==null) throw new IllegalArgumentException("Target unit cannot be null");

		double baseValue= this.toBaseUnit();
		double converted= targetUnit.convertFromBaseUnit(baseValue);

		// Round to 2 decimal places
		converted= Math.round(converted*100.0)/100.0;

		return new Quantity<>(converted, targetUnit);
	}

	public Quantity<U> add(Quantity<U> other, U targetUnit) {

		if (other==null) throw new IllegalArgumentException("Other quantity cannot be null");

		double baseSum= this.toBaseUnit()+other.toBaseUnit();

		double converted= targetUnit.convertFromBaseUnit(baseSum);

		converted= Math.round(converted*100.0)/100.0;

		return new Quantity<>(converted, targetUnit);
	}

	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

		if (other==null) throw new IllegalArgumentException("Other quantity cannot be null");

		if (targetUnit==null) throw new IllegalArgumentException("Target unit cannot be null");

		if (this.unit.getClass()!=other.unit.getClass())
			throw new IllegalArgumentException("Cannot subtract quantities of different types");

		double baseDifference= this.toBaseUnit()-other.toBaseUnit();

		double converted= targetUnit.convertFromBaseUnit(baseDifference);

		converted= Math.round(converted*100.0)/100.0;

		return new Quantity<>(converted, targetUnit);
	}

	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	public double divide(Quantity<U> other) {

		if (other==null) throw new IllegalArgumentException("Other quantity cannot be null");

		if (this.unit.getClass()!=other.unit.getClass())
			throw new IllegalArgumentException("Cannot divide quantities of different types");

		double otherBase= other.toBaseUnit();

		if (Math.abs(otherBase)<epsilon)
			throw new ArithmeticException("Division by zero is not allowed");

		return this.toBaseUnit()/otherBase;
	}

	@Override
	public boolean equals(Object o) {

		if (this==o) return true;

		if (o==null || getClass()!=o.getClass()) return false;

		Quantity<?> that= (Quantity<?>) o;

		if (this.unit.getClass()!=that.unit.getClass()) return false;

		return Math.abs(this.toBaseUnit()-that.toBaseUnit())<epsilon;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(this.toBaseUnit());
	}

	@Override
	public String toString() {
		return value +":"+ unit.getUnitName();
	}
}