package com.apps.quantitymeasurement;

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
	}
}