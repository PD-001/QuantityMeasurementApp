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

	public static void main(String[] args) {

		// Length
		demonstrateComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);

		// Weight
		demonstrateComparison(1.0, WeightUnit.KILOGRAM, 1000.0, WeightUnit.GRAM);
		
		// Volume
		demonstrateComparison(1, VolumeUnit.LITRE, 1000, VolumeUnit.MILLILITRE);
		demonstrateComparison(1, VolumeUnit.LITRE, 0.264172, VolumeUnit.GALLON);
		
		demonstrateConversion(new Quantity<>(1.0,VolumeUnit.GALLON), VolumeUnit.MILLILITRE);

	}
}