package com.apps.quantitymeasurement;

import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

	private final double value;
	private final U unit;

	private static final double epsilon= 0.0001;

	private enum ArithmeticOperation {

		ADD((a, b) -> a+b),
		SUBTRACT((a, b) -> a-b),
		DIVIDE((a, b) -> a/b);

		private final DoubleBinaryOperator operator;

		ArithmeticOperation(DoubleBinaryOperator operator) {
			this.operator= operator;
		}

		public double compute(double a, double b) {
			return operator.applyAsDouble(a, b);
		}
	}

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

	private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {

		if (other==null) throw new IllegalArgumentException("Other quantity cannot be null");

		if (other.unit==null) throw new IllegalArgumentException("Other quantity unit cannot be null");

		if (this.unit.getClass()!=other.unit.getClass())
			throw new IllegalArgumentException("Cannot perform arithmetic on quantities of different types");

		if (!Double.isFinite(this.value)) throw new IllegalArgumentException("This quantity value must be finite");

		if (!Double.isFinite(other.value)) throw new IllegalArgumentException("Other quantity value must be finite");

		if (targetUnitRequired && targetUnit==null)
			throw new IllegalArgumentException("Target unit cannot be null");
	}

	private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {

		if (operation==ArithmeticOperation.DIVIDE && Math.abs(other.toBaseUnit())<epsilon)
			throw new ArithmeticException("Division by zero is not allowed");

		return operation.compute(this.toBaseUnit(), other.toBaseUnit());
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

		validateArithmeticOperands(other, targetUnit, true);

		double baseResult= performBaseArithmetic(other, ArithmeticOperation.ADD);

		double converted= Math.round(targetUnit.convertFromBaseUnit(baseResult)*100.0)/100.0;

		return new Quantity<>(converted, targetUnit);
	}

	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {

		validateArithmeticOperands(other, targetUnit, true);

		double baseResult= performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);

		double converted= Math.round(targetUnit.convertFromBaseUnit(baseResult)*100.0)/100.0;

		return new Quantity<>(converted, targetUnit);
	}

	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	public double divide(Quantity<U> other) {

		validateArithmeticOperands(other, null, false);

		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
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