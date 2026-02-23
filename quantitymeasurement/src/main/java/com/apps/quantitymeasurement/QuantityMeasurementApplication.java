package com.apps.quantitymeasurement;

public class QuantityMeasurementApplication {

	public static boolean demonstrateLengthEquality(Length length1, Length length2) {
		return length1.equals(length2);
	}

	public static boolean demonstrateLengthComparison(double value1, LengthUnit unit1, double value2, LengthUnit unit2) {

		Length l1= new Length(value1, unit1);
		Length l2= new Length(value2, unit2);

		boolean result= l1.equals(l2);

		System.out.println("Comparing "+l1+" and "+l2+" : "+result);

		return result;
	}
	
	public static Length demonstrateLengthConversion(Length l1, LengthUnit unit) {
		
		Length newLength=l1.convertTo(unit);
		
		System.out.println("Converted length of l1 from "+ l1.getUnit() +"to "+ unit +"is "+ newLength.getValue());
		
		return newLength;
		
	}
	
	public static double demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
		
		double newLength= Length.convert(value, from, to);
		
		System.out.println("Converted length of "+ value +" from "+ from +"to "+ to +"is "+ newLength);
		
		return newLength;
	}
	
	public static Length demonstrateLengthAddition(Length l1, Length l2) {
		
		Length r1= l1.add(l2);
		
		System.out.println("Result in unit of the first length:\n" + r1);
		return r1;
		
	}

	public static Length demonstrateLengthAddition(Length l1, Length l2, LengthUnit targetUnit) {
		
		Length r1= Length.add(l1, l2, targetUnit);
		
		System.out.println("Result in target unit:\n" + r1);
		
		return r1;
		
	}
	
	public static void main(String[] args) {

		demonstrateLengthComparison(1.0, LengthUnit.FEET, 12.0, LengthUnit.INCHES);

		demonstrateLengthComparison(1.0, LengthUnit.YARDS, 36.0, LengthUnit.INCHES);

		demonstrateLengthComparison(100.0, LengthUnit.CENTIMETERS, 39.3701, LengthUnit.INCHES);

		demonstrateLengthComparison(3.0, LengthUnit.FEET, 1.0, LengthUnit.YARDS);

		demonstrateLengthComparison(30.48, LengthUnit.CENTIMETERS, 1.0, LengthUnit.FEET);
		
		demonstrateLengthConversion(10, LengthUnit.FEET, LengthUnit.INCHES);
		
		demonstrateLengthConversion(new Length(10, LengthUnit.FEET), LengthUnit.INCHES);
		
		demonstrateLengthAddition(new Length(10, LengthUnit.FEET), new Length(20, LengthUnit.INCHES));

		demonstrateLengthAddition(new Length(10, LengthUnit.FEET), new Length(20, LengthUnit.INCHES), LengthUnit.CENTIMETERS);

	}

}
