package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.Length.LengthUnit;

public class QuantityMeasurementApplication {

	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.equals(length2);
	}

	public static boolean demonstrateLengthComparison(double value1, Length.LengthUnit unit1, double value2, Length.LengthUnit unit2) {

		Length l1= new Length(value1, unit1);
		Length l2= new Length(value2, unit2);

		boolean result= l1.equals(l2);

		System.out.println("Comparing "+l1+" and "+l2+" : "+result);

		return result;
	}
	
	public static Length demonstrateLengthConversion(Length l1, Length.LengthUnit unit) {
		
		Length newLength=l1.convertTo(unit);
		
		System.out.println("Converted length of l1 from "+ l1.getUnit() +"to "+ unit +"is "+ newLength.getValue());
		
		return newLength;
		
	}
	
	public static double demonstrateLengthConversion(double value, Length.LengthUnit from, Length.LengthUnit to) {
		
		double newLength= Length.convert(value, from, to);
		
		System.out.println("Converted length of "+ value +" from "+ from +"to "+ to +"is "+ newLength);
		
		return newLength;
	}
	
	public static Length demonstrateLengthAddition(Length l1, Length l2) {
		
		Length r1= l1.add(l2);
		
		System.out.println("Result in unit of the first length:\n" + r1);
		return r1;
		
	}

	public static Length demonstrateLengthAddition(Length l1, Length l2, Length.LengthUnit targetUnit) {
		
		Length r1= Length.add(l1, l2, targetUnit);
		
		System.out.println("Result in target unit:\n" + r1);
		
		return r1;
		
	}
	
	public static void main(String[] args) {

		demonstrateLengthComparison(1.0, Length.LengthUnit.FEET, 12.0, Length.LengthUnit.INCHES);

		demonstrateLengthComparison(1.0, Length.LengthUnit.YARDS, 36.0, Length.LengthUnit.INCHES);

		demonstrateLengthComparison(100.0, Length.LengthUnit.CENTIMETERS, 39.3701, Length.LengthUnit.INCHES);

		demonstrateLengthComparison(3.0, Length.LengthUnit.FEET, 1.0, Length.LengthUnit.YARDS);

		demonstrateLengthComparison(30.48, Length.LengthUnit.CENTIMETERS, 1.0, Length.LengthUnit.FEET);
		
		demonstrateLengthConversion(10, Length.LengthUnit.FEET, Length.LengthUnit.INCHES);
		
		demonstrateLengthConversion(new Length(10, Length.LengthUnit.FEET), Length.LengthUnit.INCHES);
		
		demonstrateLengthAddition(new Length(10, Length.LengthUnit.FEET), new Length(20, Length.LengthUnit.INCHES));

		demonstrateLengthAddition(new Length(10, Length.LengthUnit.FEET), new Length(20, Length.LengthUnit.INCHES), LengthUnit.CENTIMETERS);

	}

}
