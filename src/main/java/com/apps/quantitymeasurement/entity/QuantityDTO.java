package com.apps.quantitymeasurement.entity;

public class QuantityDTO {

	public interface IMeasurableUnit {
		String getUnitName();
		String getMeasurementType();
	}

	public enum LengthUnit implements IMeasurableUnit {
		INCHES, FEET, YARDS, CENTIMETERS;

		@Override
		public String getUnitName() { return this.name(); }

		@Override
		public String getMeasurementType() { return "LENGTH"; }
	}

	public enum WeightUnit implements IMeasurableUnit {
		GRAM, KILOGRAM, POUND;

		@Override
		public String getUnitName() { return this.name(); }

		@Override
		public String getMeasurementType() { return "WEIGHT"; }
	}

	public enum VolumeUnit implements IMeasurableUnit {
		LITRE, MILLILITRE, GALLON;

		@Override
		public String getUnitName() { return this.name(); }

		@Override
		public String getMeasurementType() { return "VOLUME"; }
	}

	public enum TemperatureUnit implements IMeasurableUnit {
		CELSIUS, FAHRENHEIT;

		@Override
		public String getUnitName() { return this.name(); }

		@Override
		public String getMeasurementType() { return "TEMPERATURE"; }
	}

	private final double value;
	private final IMeasurableUnit unit;

	public QuantityDTO(double value, IMeasurableUnit unit) {

		if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");

		if (unit==null) throw new IllegalArgumentException("Unit cannot be null");

		this.value= value;
		this.unit= unit;
	}

	public double getValue() {
		return value;
	}

	public IMeasurableUnit getUnit() {
		return unit;
	}

	@Override
	public String toString() {
		return value +":"+ unit.getUnitName();
	}
}