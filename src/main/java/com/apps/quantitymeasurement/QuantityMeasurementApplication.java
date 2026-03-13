package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.core.IMeasurable;
import com.apps.quantitymeasurement.core.LengthUnit;
import com.apps.quantitymeasurement.core.Quantity;
import com.apps.quantitymeasurement.core.TemperatureUnit;
import com.apps.quantitymeasurement.core.VolumeUnit;
import com.apps.quantitymeasurement.core.WeightUnit;

public class QuantityMeasurementApplication {

	public static <U extends IMeasurable> boolean demonstrateEquality(Quantity<U> q1, Quantity<U> q2) {

		return q1.equals(q2);
	}

	public static <U extends IMeasurable> void demonstrateComparison(double value1, U unit1, double value2, U unit2) {

		Quantity<U> q1= new Quantity<>(value1, unit1);
		Quantity<U> q2= new Quantity<>(value2, unit2);

		boolean result= q1.equals(q2);

		System.out.println("Comparing "+ q1 +" and "+ q2 +" : "+ result);
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateConversion(Quantity<U> quantity, U targetUnit) {

		Quantity<U> result= quantity.convertTo(targetUnit);

		System.out.println("Converted: " + result);

		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateAddition(Quantity<U> q1, Quantity<U> q2) {

		Quantity<U> result= q1.add(q2);

		System.out.println("Addition result: " + result);

		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2) {

		Quantity<U> result= q1.subtract(q2);

		System.out.println("Subtraction result: " + result);

		return result;
	}

	public static <U extends IMeasurable> Quantity<U> demonstrateSubtraction(Quantity<U> q1, Quantity<U> q2, U targetUnit) {

		Quantity<U> result= q1.subtract(q2, targetUnit);

		System.out.println("Subtraction result: " + result);

		return result;
	}

	public static <U extends IMeasurable> double demonstrateDivision(Quantity<U> q1, Quantity<U> q2) {

		double result= q1.divide(q2);

		System.out.println("Division result: " + result);

		return result;
	}

	public static void main(String[] args) {

		demonstrateComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);

		demonstrateComparison(1.0, WeightUnit.KILOGRAM, 1000.0, WeightUnit.GRAM);

		demonstrateComparison(1, VolumeUnit.LITRE, 1000, VolumeUnit.MILLILITRE);
		demonstrateComparison(1, VolumeUnit.LITRE, 0.264172, VolumeUnit.GALLON);

		demonstrateConversion(new Quantity<>(1.0, VolumeUnit.GALLON), VolumeUnit.MILLILITRE);

		demonstrateSubtraction(new Quantity<>(5.0, LengthUnit.FEET), new Quantity<>(12.0, LengthUnit.INCHES));
		demonstrateSubtraction(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(500.0, WeightUnit.GRAM));
		demonstrateSubtraction(new Quantity<>(5.0, VolumeUnit.LITRE), new Quantity<>(500.0, VolumeUnit.MILLILITRE), VolumeUnit.LITRE);

		demonstrateDivision(new Quantity<>(10.0, WeightUnit.KILOGRAM), new Quantity<>(5.0, WeightUnit.KILOGRAM));
		demonstrateDivision(new Quantity<>(1.0, LengthUnit.FEET), new Quantity<>(6.0, LengthUnit.INCHES));

		demonstrateComparison(100.0, TemperatureUnit.CELSIUS, 212.0, TemperatureUnit.FAHRENHEIT);
		demonstrateComparison(0.0, TemperatureUnit.CELSIUS, 32.0, TemperatureUnit.FAHRENHEIT);

		demonstrateConversion(new Quantity<>(100.0, TemperatureUnit.CELSIUS), TemperatureUnit.FAHRENHEIT);
		demonstrateConversion(new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT), TemperatureUnit.CELSIUS);

		try {
			demonstrateAddition(
				new Quantity<>(100.0, TemperatureUnit.CELSIUS),
				new Quantity<>(50.0, TemperatureUnit.CELSIUS)
			);
		} catch (UnsupportedOperationException e) {
			System.out.println("Expected error for temperature addition: " + e.getMessage());
		}

		try {
			demonstrateDivision(
				new Quantity<>(100.0, TemperatureUnit.CELSIUS),
				new Quantity<>(50.0, TemperatureUnit.CELSIUS)
			);
		} catch (UnsupportedOperationException e) {
			System.out.println("Expected error for temperature division: " + e.getMessage());
		}
	}
}