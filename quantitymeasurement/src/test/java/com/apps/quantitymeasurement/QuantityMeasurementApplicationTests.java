package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementApplicationTests {

	@Test
	void kilogramToGramConversion() {

		Quantity<WeightUnit> weight= new Quantity<>(2.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> converted= weight.convertTo(WeightUnit.GRAM);

		assertEquals(2000.0, converted.getValue(), 0.0001);
	}

	@Test
	void gramToKilogramConversion() {

		Quantity<WeightUnit> weight= new Quantity<>(500.0, WeightUnit.GRAM);

		Quantity<WeightUnit> converted= weight.convertTo(WeightUnit.KILOGRAM);

		assertEquals(0.5, converted.getValue(), 0.0001);
	}

	@Test
	void poundToKilogramConversion() {

		Quantity<WeightUnit> weight= new Quantity<>(1.0, WeightUnit.POUND);

		Quantity<WeightUnit> converted= weight.convertTo(WeightUnit.KILOGRAM);

		assertEquals(0.45, converted.getValue(), 0.0001);
	}

	@Test
	void additionOfWeightsEqualsExpected() {

		Quantity<WeightUnit> w1= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> w2= new Quantity<>(500.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result= w1.add(w2);

		assertEquals(new Quantity<>(1.5, WeightUnit.KILOGRAM), result);
	}

	@Test
	void feetToInchesConversion() {

		Quantity<LengthUnit> length= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> converted= length.convertTo(LengthUnit.INCHES);

		assertEquals(12.0, converted.getValue());
	}

	@Test
	void yardsToFeetConversion() {

		Quantity<LengthUnit> length= new Quantity<>(1.0, LengthUnit.YARDS);

		Quantity<LengthUnit> converted= length.convertTo(LengthUnit.FEET);

		assertEquals(3.0, converted.getValue());
	}

	@Test
	void roundTripConversion() {

		Quantity<LengthUnit> original= new Quantity<>(5.0, LengthUnit.FEET);

		Quantity<LengthUnit> converted=
				original.convertTo(LengthUnit.INCHES)
						.convertTo(LengthUnit.FEET);

		assertEquals(original, converted);
	}

	@Test
	void addFeetAndInches() {

		Quantity<LengthUnit> l1= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> l2= new Quantity<>(12.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result= l1.add(l2);

		assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
	}

	@Test
	void addWithTargetUnit() {

		Quantity<LengthUnit> l1= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> l2= new Quantity<>(12.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result= l1.add(l2, LengthUnit.CENTIMETERS);

		assertEquals(60.96, result.getValue(), 0.01);
	}

	@Test
	void lengthNotEqualToWeight() {

		Quantity<LengthUnit> length= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<WeightUnit> weight= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertNotEquals(length, weight);
	}

	@Test
	void constructorNullUnitThrows() {
		assertThrows(IllegalArgumentException.class, ()->new Quantity<>(1.0, null));
	}

	@Test
	void constructorNaNThrows() {
		assertThrows(IllegalArgumentException.class, ()->new Quantity<>(Double.NaN, LengthUnit.FEET));
	}
}