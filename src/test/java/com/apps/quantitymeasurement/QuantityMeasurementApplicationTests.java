package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {
	
	double EPSILON= 0.001;


	@Test
	void lengthFeetEqualsInches() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(12.0, LengthUnit.INCHES);

		assertTrue(feet.equals(inches));
	}

	@Test
	void lengthYardsEqualsFeet() {

		Quantity<LengthUnit> yards= new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> feet= new Quantity<>(3.0, LengthUnit.FEET);

		assertTrue(yards.equals(feet));
	}


	@Test
	void convertLengthFeetToInches() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> converted= feet.convertTo(LengthUnit.INCHES);

		assertEquals(12.0, converted.getValue(), 0.0001);
	}

	@Test
	void convertLengthYardsToInches() {

		Quantity<LengthUnit> yards= new Quantity<>(1.0, LengthUnit.YARDS);

		Quantity<LengthUnit> converted= yards.convertTo(LengthUnit.INCHES);

		assertEquals(36.0, converted.getValue(), 0.0001);
	}


	@Test
	void addLengthFeetAndInches() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(12.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result= feet.add(inches);

		assertEquals(new Quantity<>(24.0, LengthUnit.INCHES), result);
	}

	@Test
	void addLengthYardsAndFeet() {

		Quantity<LengthUnit> yards= new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> result= yards.add(feet);

		assertEquals(new Quantity<>(47.88, LengthUnit.INCHES), result.convertTo(LengthUnit.INCHES));
	}


	@Test
	void weightKilogramEqualsGrams() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(1000.0, WeightUnit.GRAM);

		assertTrue(kilogram.equals(grams));
	}

	@Test
	void weightPoundEqualsGrams() {

		Quantity<WeightUnit> pound= new Quantity<>(1.0, WeightUnit.POUND);
		Quantity<WeightUnit> grams= new Quantity<>(453.592, WeightUnit.GRAM);

		assertTrue(pound.equals(grams));
	}


	@Test
	void convertWeightKilogramsToGrams() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> converted= kilogram.convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, converted.getValue(), 0.0001);
	}


	@Test
	void addWeightKilogramsAndGrams() {

		Quantity<WeightUnit> kilograms= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(1000.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result= kilograms.add(grams);

		assertEquals(new Quantity<>(2.0, WeightUnit.KILOGRAM), result);
	}

	@Test
	void addWeightKilogramsAndPounds() {

		Quantity<WeightUnit> kilograms= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> pounds= new Quantity<>(1.0, WeightUnit.POUND);

		Quantity<WeightUnit> result= kilograms.add(pounds);

		assertEquals(new Quantity<>(1450.00, WeightUnit.GRAM), result.convertTo(WeightUnit.GRAM));
	}

	@Test
	void addWeightTonnesAndKilograms() {

		Quantity<WeightUnit> kilograms1= new Quantity<>(1000.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> kilograms2= new Quantity<>(1000.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> result= kilograms1.add(kilograms2);

		assertEquals(new Quantity<>(2000.0, WeightUnit.KILOGRAM), result);
	}


	@Test
	void volumeLiterEqualsMilliliters() {

		Quantity<VolumeUnit> litre= new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		assertTrue(litre.equals(millilitre));
	}


	@Test
	void convertVolumeLitersToMilliliters() {

		Quantity<VolumeUnit> litre= new Quantity<>(1.0, VolumeUnit.LITRE);

		Quantity<VolumeUnit> converted= litre.convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.0, converted.getValue(), 0.0001);
	}


	@Test
	void addVolumeLitersAndMilliliters() {

		Quantity<VolumeUnit> litre= new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result= litre.add(millilitre);

		assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), result);
	}


	@Test
	void preventCrossTypeComparisonLengthVsWeight() {

		Quantity<LengthUnit> length= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(length.equals(weight));
	}
	

	@Test
	void testGenericTypeSafetyWithWeight() {

		Quantity<WeightUnit> weight= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertNotNull(weight);
		assertEquals(WeightUnit.KILOGRAM, weight.getUnit());
	}


	@Test
	void backwardCompatibilityLengthFeetEqualsInches() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(12.0, LengthUnit.INCHES);

		assertTrue(QuantityMeasurementApplication.demonstrateEquality(feet, inches));
	}

	@Test
	void backwardCompatibilityLengthYardsEqualsFeet() {

		Quantity<LengthUnit> yards= new Quantity<>(1.0, LengthUnit.YARDS);
		Quantity<LengthUnit> feet= new Quantity<>(3.0, LengthUnit.FEET);

		assertTrue(QuantityMeasurementApplication.demonstrateEquality(yards, feet));
	}

	@Test
	void backwardCompatibilityConvertLengthFeetToInches() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> result= QuantityMeasurementApplication.demonstrateConversion(feet, LengthUnit.INCHES);

		assertEquals(12.0, result.getValue(), 0.0001);
	}

	@Test
	void backwardCompatibilityAddLengthInSameUnit() {

		Quantity<LengthUnit> feet1= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> feet2= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> result= QuantityMeasurementApplication.demonstrateAddition(feet1, feet2);

		assertEquals(new Quantity<>(2.0, LengthUnit.FEET), result);
	}

	@Test
	void backwardCompatibilityChainedAdditionsLength() {

		Quantity<LengthUnit> inches1= new Quantity<>(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> inches2= new Quantity<>(12.0, LengthUnit.INCHES);
		Quantity<LengthUnit> inches3= new Quantity<>(12.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result= inches1.add(inches2).add(inches3);

		assertEquals(new Quantity<>(3.0, LengthUnit.FEET), result.convertTo(LengthUnit.FEET));
	}

	@Test
	void backwardCompatibilityWeightKilogramEqualsGrams() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(1000.0, WeightUnit.GRAM);

		assertTrue(QuantityMeasurementApplication.demonstrateEquality(kilogram, grams));
	}

	@Test
	void backwardCompatibilityConvertWeightKilogramsToGrams() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> result= QuantityMeasurementApplication.demonstrateConversion(kilogram, WeightUnit.GRAM);

		assertEquals(1000.0, result.getValue(), 0.0001);
	}

	@Test
	void backwardCompatibilityWeightPoundEqualsGrams() {

		Quantity<WeightUnit> pound= new Quantity<>(1.0, WeightUnit.POUND);
		Quantity<WeightUnit> grams= new Quantity<>(453.592, WeightUnit.GRAM);

		assertTrue(QuantityMeasurementApplication.demonstrateEquality(pound, grams));
	}

	@Test
	void backwardCompatibilityAddWeightInSameUnit() {

		Quantity<WeightUnit> kg1= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> kg2= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> result= QuantityMeasurementApplication.demonstrateAddition(kg1, kg2);

		assertEquals(new Quantity<>(2.0, WeightUnit.KILOGRAM), result);
	}

	@Test
	void subtractLengthSameUnit() {

		Quantity<LengthUnit> feet1= new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> feet2= new Quantity<>(2.0, LengthUnit.FEET);

		Quantity<LengthUnit> result= feet1.subtract(feet2);

		assertEquals(new Quantity<>(3.0, LengthUnit.FEET), result);
	}

	@Test
	void subtractLengthCrossUnit() {

		Quantity<LengthUnit> feet= new Quantity<>(2.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(6.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result= feet.subtract(inches);

		assertEquals(new Quantity<>(18.0, LengthUnit.INCHES), result.convertTo(LengthUnit.INCHES));
	}

	@Test
	void subtractLengthWithExplicitTargetUnit() {

		Quantity<LengthUnit> yards= new Quantity<>(2.0, LengthUnit.YARDS);
		Quantity<LengthUnit> feet= new Quantity<>(3.0, LengthUnit.FEET);

		Quantity<LengthUnit> result= yards.subtract(feet, LengthUnit.FEET);

		assertEquals(new Quantity<>(3.0, LengthUnit.FEET), result);
	}

	@Test
	void subtractLengthNegativeResult() {

		Quantity<LengthUnit> inches= new Quantity<>(6.0, LengthUnit.INCHES);
		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);

		Quantity<LengthUnit> result= inches.subtract(feet);

		assertEquals(new Quantity<>(-6.0, LengthUnit.INCHES), result);
	}

	@Test
	void subtractLengthZeroResult() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(12.0, LengthUnit.INCHES);

		Quantity<LengthUnit> result= feet.subtract(inches);

		assertEquals(new Quantity<>(0.0, LengthUnit.FEET), result);
	}


	@Test
	void subtractWeightSameUnit() {

		Quantity<WeightUnit> kg1= new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> kg2= new Quantity<>(3.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> result= kg1.subtract(kg2);

		assertEquals(new Quantity<>(7.0, WeightUnit.KILOGRAM), result);
	}

	@Test
	void subtractWeightCrossUnit() {

		Quantity<WeightUnit> kilogram= new Quantity<>(2.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(500.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result= kilogram.subtract(grams);

		assertEquals(new Quantity<>(1.5, WeightUnit.KILOGRAM), result);
	}

	@Test
	void subtractWeightWithExplicitTargetUnit() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(250.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result= kilogram.subtract(grams, WeightUnit.GRAM);

		assertEquals(new Quantity<>(750.0, WeightUnit.GRAM), result);
	}


	@Test
	void subtractVolumeSameUnit() {

		Quantity<VolumeUnit> litre1= new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> litre2= new Quantity<>(2.0, VolumeUnit.LITRE);

		Quantity<VolumeUnit> result= litre1.subtract(litre2);

		assertEquals(new Quantity<>(3.0, VolumeUnit.LITRE), result);
	}

	@Test
	void subtractVolumeCrossUnit() {

		Quantity<VolumeUnit> litre= new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result= litre.subtract(millilitre);

		assertEquals(new Quantity<>(4.5, VolumeUnit.LITRE), result);
	}

	@Test
	void subtractVolumeWithExplicitTargetUnit() {

		Quantity<VolumeUnit> litre= new Quantity<>(2.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result= litre.subtract(millilitre, VolumeUnit.MILLILITRE);

		assertEquals(new Quantity<>(1500.0, VolumeUnit.MILLILITRE), result);
	}


	@Test
	void divideLengthSameUnit() {

		Quantity<LengthUnit> feet1= new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> feet2= new Quantity<>(2.0, LengthUnit.FEET);

		double result= feet1.divide(feet2);

		assertEquals(5.0, result, 0.0001);
	}

	@Test
	void divideLengthCrossUnit() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(6.0, LengthUnit.INCHES);

		double result= feet.divide(inches);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	void divideLengthResultLessThanOne() {

		Quantity<LengthUnit> inches= new Quantity<>(6.0, LengthUnit.INCHES);
		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);

		double result= inches.divide(feet);

		assertEquals(0.5, result, 0.0001);
	}

	@Test
	void divideLengthEqualQuantitiesReturnsOne() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(12.0, LengthUnit.INCHES);

		double result= feet.divide(inches);

		assertEquals(1.0, result, 0.0001);
	}


	@Test
	void divideWeightSameUnit() {

		Quantity<WeightUnit> kg1= new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> kg2= new Quantity<>(5.0, WeightUnit.KILOGRAM);

		double result= kg1.divide(kg2);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	void divideWeightCrossUnit() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(500.0, WeightUnit.GRAM);

		double result= kilogram.divide(grams);

		assertEquals(2.0, result, 0.0001);
	}


	@Test
	void divideVolumeSameUnit() {

		Quantity<VolumeUnit> litre1= new Quantity<>(6.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> litre2= new Quantity<>(2.0, VolumeUnit.LITRE);

		double result= litre1.divide(litre2);

		assertEquals(3.0, result, 0.0001);
	}

	@Test
	void divideVolumeCrossUnit() {

		Quantity<VolumeUnit> litre= new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		double result= litre.divide(millilitre);

		assertEquals(2.0, result, 0.0001);
	}


	@Test
	void divideByZeroThrowsArithmeticException() {

		Quantity<WeightUnit> kilogram= new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> zero= new Quantity<>(0.0, WeightUnit.KILOGRAM);

		assertThrows(ArithmeticException.class, () -> kilogram.divide(zero));
	}


	@Test
	void subtractNullThrowsIllegalArgumentException() {

		Quantity<LengthUnit> feet= new Quantity<>(5.0, LengthUnit.FEET);

		assertThrows(IllegalArgumentException.class, () -> feet.subtract(null));
	}


	@Test
	void preventCrossTypeSubtractionLengthVsWeight() {

		assertThrows(IllegalArgumentException.class, () -> {

			Quantity length= new Quantity<>(5.0, LengthUnit.FEET);
			Quantity weight= new Quantity<>(1.0, WeightUnit.KILOGRAM);

			length.subtract(weight);
		});
	}

	@Test
	void preventCrossTypeDivisionLengthVsWeight() {

		assertThrows(IllegalArgumentException.class, () -> {

			Quantity length= new Quantity<>(5.0, LengthUnit.FEET);
			Quantity weight= new Quantity<>(1.0, WeightUnit.KILOGRAM);

			length.subtract(weight);
		});

	}


	@Test
	void subtractionIsNonCommutative() {

		Quantity<WeightUnit> kg1= new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> kg2= new Quantity<>(3.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> result1= kg1.subtract(kg2);
		Quantity<WeightUnit> result2= kg2.subtract(kg1);

		assertFalse(result1.equals(result2));
	}

	@Test
	void divisionIsNonCommutative() {

		Quantity<LengthUnit> feet= new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(24.0, LengthUnit.INCHES);

		double result1= feet.divide(inches);
		double result2= inches.divide(feet);

		assertNotEquals(result1, result2, 0.0001);
	}


	@Test
	void subtractDoesNotMutateOriginalQuantities() {

		Quantity<VolumeUnit> litre= new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		litre.subtract(millilitre);

		assertEquals(5.0, litre.getValue(), 0.0001);
		assertEquals(500.0, millilitre.getValue(), 0.0001);
	}


	@Test
	void backwardCompatibilitySubtractLengthSameUnit() {

		Quantity<LengthUnit> feet1= new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> feet2= new Quantity<>(2.0, LengthUnit.FEET);

		Quantity<LengthUnit> result= QuantityMeasurementApplication.demonstrateSubtraction(feet1, feet2);

		assertEquals(new Quantity<>(3.0, LengthUnit.FEET), result);
	}

	@Test
	void backwardCompatibilitySubtractWeightWithTargetUnit() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(250.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result= QuantityMeasurementApplication.demonstrateSubtraction(kilogram, grams, WeightUnit.GRAM);

		assertEquals(new Quantity<>(750.0, WeightUnit.GRAM), result);
	}

	@Test
	void backwardCompatibilityDivideWeight() {

		Quantity<WeightUnit> kg1= new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> kg2= new Quantity<>(5.0, WeightUnit.KILOGRAM);

		double result= QuantityMeasurementApplication.demonstrateDivision(kg1, kg2);

		assertEquals(2.0, result, 0.0001);
	}
}