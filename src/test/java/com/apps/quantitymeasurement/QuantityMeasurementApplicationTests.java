package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementAppTest {
	
	double EPSILON= 0.001;

	// ─── LENGTH EQUALITY ────────────────────────────────────────────────────────

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

	// ─── LENGTH CONVERSION ──────────────────────────────────────────────────────

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

	// ─── LENGTH ADDITION ────────────────────────────────────────────────────────

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

	// ─── WEIGHT EQUALITY ────────────────────────────────────────────────────────

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

	// ─── WEIGHT CONVERSION ──────────────────────────────────────────────────────

	@Test
	void convertWeightKilogramsToGrams() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> converted= kilogram.convertTo(WeightUnit.GRAM);

		assertEquals(1000.0, converted.getValue(), 0.0001);
	}

	// ─── WEIGHT ADDITION ────────────────────────────────────────────────────────

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

	// ─── VOLUME EQUALITY ────────────────────────────────────────────────────────

	@Test
	void volumeLiterEqualsMilliliters() {

		Quantity<VolumeUnit> litre= new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		assertTrue(litre.equals(millilitre));
	}

	// ─── VOLUME CONVERSION ──────────────────────────────────────────────────────

	@Test
	void convertVolumeLitersToMilliliters() {

		Quantity<VolumeUnit> litre= new Quantity<>(1.0, VolumeUnit.LITRE);

		Quantity<VolumeUnit> converted= litre.convertTo(VolumeUnit.MILLILITRE);

		assertEquals(1000.0, converted.getValue(), 0.0001);
	}

	// ─── VOLUME ADDITION ────────────────────────────────────────────────────────

	@Test
	void addVolumeLitersAndMilliliters() {

		Quantity<VolumeUnit> litre= new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(1000.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result= litre.add(millilitre);

		assertEquals(new Quantity<>(2.0, VolumeUnit.LITRE), result);
	}

	// ─── CROSS-TYPE PREVENTION ──────────────────────────────────────────────────

	@Test
	void preventCrossTypeComparisonLengthVsWeight() {

		Quantity<LengthUnit> length= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertFalse(length.equals(weight));
	}
	
	// ─── TYPE SAFETY ────────────────────────────────────────────────────────────

	@Test
	void testGenericTypeSafetyWithWeight() {

		Quantity<WeightUnit> weight= new Quantity<>(1.0, WeightUnit.KILOGRAM);

		assertNotNull(weight);
		assertEquals(WeightUnit.KILOGRAM, weight.getUnit());
	}

	// ─── BACKWARD COMPATIBILITY ─────────────────────────────────────────────────

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
}