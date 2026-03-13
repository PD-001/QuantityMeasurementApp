package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.core.LengthUnit;
import com.apps.quantitymeasurement.core.Quantity;
import com.apps.quantitymeasurement.core.TemperatureUnit;
import com.apps.quantitymeasurement.core.VolumeUnit;
import com.apps.quantitymeasurement.core.WeightUnit;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImplementation;

import static org.junit.jupiter.api.Assertions.*;

class QuantityMeasurementApplicationTests {

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

		assertEquals(new Quantity<>(1450.0, WeightUnit.GRAM), result.convertTo(WeightUnit.GRAM));
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
	void preventCrossTypeAdditionLengthVsWeight() {

		assertThrows(IllegalArgumentException.class, () -> {

			Quantity length= new Quantity<>(1.0, LengthUnit.FEET);
			Quantity weight= new Quantity<>(1.0, WeightUnit.KILOGRAM);

			length.add(weight);
		});
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

			Quantity length= new Quantity<>(10.0, LengthUnit.FEET);
			Quantity weight= new Quantity<>(5.0, WeightUnit.KILOGRAM);

			length.divide(weight);
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

	@Test
	void testSubtractionOfWeightsInSameUnit() {

		Quantity<WeightUnit> kg1= new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> kg2= new Quantity<>(3.0, WeightUnit.KILOGRAM);

		Quantity<WeightUnit> result= kg1.subtract(kg2);

		assertEquals(new Quantity<>(7.0, WeightUnit.KILOGRAM), result);
	}

	@Test
	void testSubtractionOfWeightsWithDifferentUnits() {

		Quantity<WeightUnit> kilogram= new Quantity<>(2.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(500.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result= kilogram.subtract(grams);

		assertEquals(new Quantity<>(1.5, WeightUnit.KILOGRAM), result);
	}

	@Test
	void testSubtractionOfVolumesInSameUnit() {

		Quantity<VolumeUnit> litre1= new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> litre2= new Quantity<>(2.0, VolumeUnit.LITRE);

		Quantity<VolumeUnit> result= litre1.subtract(litre2);

		assertEquals(new Quantity<>(3.0, VolumeUnit.LITRE), result);
	}

	@Test
	void testSubtractionOfVolumesWithDifferentUnits() {

		Quantity<VolumeUnit> litre= new Quantity<>(5.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		Quantity<VolumeUnit> result= litre.subtract(millilitre);

		assertEquals(new Quantity<>(4.5, VolumeUnit.LITRE), result);
	}

	@Test
	void testSubtractionWithTargetUnit() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(250.0, WeightUnit.GRAM);

		Quantity<WeightUnit> result= kilogram.subtract(grams, WeightUnit.GRAM);

		assertEquals(new Quantity<>(750.0, WeightUnit.GRAM), result);
	}

	@Test
	void testDivisionOfWeightsInSameUnit() {

		Quantity<WeightUnit> kg1= new Quantity<>(10.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> kg2= new Quantity<>(5.0, WeightUnit.KILOGRAM);

		double result= kg1.divide(kg2);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	void testDivisionOfWeightsWithDifferentUnits() {

		Quantity<WeightUnit> kilogram= new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> grams= new Quantity<>(500.0, WeightUnit.GRAM);

		double result= kilogram.divide(grams);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	void testDivisionOfVolumesInSameUnit() {

		Quantity<VolumeUnit> litre1= new Quantity<>(6.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> litre2= new Quantity<>(2.0, VolumeUnit.LITRE);

		double result= litre1.divide(litre2);

		assertEquals(3.0, result, 0.0001);
	}

	@Test
	void testDivisionOfVolumesWithDifferentUnits() {

		Quantity<VolumeUnit> litre= new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> millilitre= new Quantity<>(500.0, VolumeUnit.MILLILITRE);

		double result= litre.divide(millilitre);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	void testDivisionWithTargetUnit() {

		Quantity<LengthUnit> feet= new Quantity<>(1.0, LengthUnit.FEET);
		Quantity<LengthUnit> inches= new Quantity<>(6.0, LengthUnit.INCHES);

		double result= feet.divide(inches);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	void temperatureCelsiusEqualsFahrenheit() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> fahrenheit= new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);

		assertTrue(celsius.equals(fahrenheit));
	}

	@Test
	void temperatureFreezingPointEquals() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> fahrenheit= new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

		assertTrue(celsius.equals(fahrenheit));
	}

	@Test
	void temperatureBodyTempEquals() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(37.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> fahrenheit= new Quantity<>(98.6, TemperatureUnit.FAHRENHEIT);

		assertTrue(celsius.equals(fahrenheit));
	}

	@Test
	void temperatureSameUnitEquals() {

		Quantity<TemperatureUnit> celsius1= new Quantity<>(25.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> celsius2= new Quantity<>(25.0, TemperatureUnit.CELSIUS);

		assertTrue(celsius1.equals(celsius2));
	}

	@Test
	void temperatureDifferentValuesNotEqual() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> fahrenheit= new Quantity<>(100.0, TemperatureUnit.FAHRENHEIT);

		assertFalse(celsius.equals(fahrenheit));
	}

	@Test
	void convertCelsiusToFahrenheit() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(100.0, TemperatureUnit.CELSIUS);

		Quantity<TemperatureUnit> converted= celsius.convertTo(TemperatureUnit.FAHRENHEIT);

		assertEquals(212.0, converted.getValue(), 0.0001);
	}

	@Test
	void convertFahrenheitToCelsius() {

		Quantity<TemperatureUnit> fahrenheit= new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);

		Quantity<TemperatureUnit> converted= fahrenheit.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(100.0, converted.getValue(), 0.0001);
	}

	@Test
	void convertFreezingPointFahrenheitToCelsius() {

		Quantity<TemperatureUnit> fahrenheit= new Quantity<>(32.0, TemperatureUnit.FAHRENHEIT);

		Quantity<TemperatureUnit> converted= fahrenheit.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(0.0, converted.getValue(), 0.0001);
	}

	@Test
	void convertNegativeCelsiusToFahrenheit() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(-40.0, TemperatureUnit.CELSIUS);

		Quantity<TemperatureUnit> converted= celsius.convertTo(TemperatureUnit.FAHRENHEIT);

		assertEquals(-40.0, converted.getValue(), 0.0001);
	}

	@Test
	void convertCelsiusToCelsiusReturnsSameValue() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(25.0, TemperatureUnit.CELSIUS);

		Quantity<TemperatureUnit> converted= celsius.convertTo(TemperatureUnit.CELSIUS);

		assertEquals(25.0, converted.getValue(), 0.0001);
	}

	@Test
	void temperatureAdditionThrowsUnsupportedOperationException() {

		Quantity<TemperatureUnit> celsius1= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> celsius2= new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		assertThrows(UnsupportedOperationException.class, () -> celsius1.add(celsius2));
	}

	@Test
	void temperatureSubtractionThrowsUnsupportedOperationException() {

		Quantity<TemperatureUnit> celsius1= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> celsius2= new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		assertThrows(UnsupportedOperationException.class, () -> celsius1.subtract(celsius2));
	}

	@Test
	void temperatureDivisionThrowsUnsupportedOperationException() {

		Quantity<TemperatureUnit> celsius1= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> celsius2= new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		assertThrows(UnsupportedOperationException.class, () -> celsius1.divide(celsius2));
	}

	@Test
	void temperatureAdditionWithExplicitTargetUnitThrowsUnsupportedOperationException() {

		Quantity<TemperatureUnit> celsius1= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> celsius2= new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		assertThrows(UnsupportedOperationException.class,
			() -> celsius1.add(celsius2, TemperatureUnit.CELSIUS));
	}

	@Test
	void preventCrossTypeComparisonTemperatureVsLength() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<LengthUnit> feet= new Quantity<>(100.0, LengthUnit.FEET);

		assertFalse(celsius.equals(feet));
	}

	@Test
	void preventCrossTypeComparisonTemperatureVsWeight() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<WeightUnit> kilogram= new Quantity<>(100.0, WeightUnit.KILOGRAM);

		assertFalse(celsius.equals(kilogram));
	}

	@Test
	void temperatureUnitDoesNotSupportArithmetic() {

		assertFalse(TemperatureUnit.CELSIUS.supportsArithmetic());
		assertFalse(TemperatureUnit.FAHRENHEIT.supportsArithmetic());
	}

	@Test
	void lengthUnitSupportsArithmetic() {

		assertTrue(LengthUnit.FEET.supportsArithmetic());
		assertTrue(LengthUnit.INCHES.supportsArithmetic());
	}

	@Test
	void weightUnitSupportsArithmetic() {

		assertTrue(WeightUnit.KILOGRAM.supportsArithmetic());
		assertTrue(WeightUnit.GRAM.supportsArithmetic());
	}

	@Test
	void volumeUnitSupportsArithmetic() {

		assertTrue(VolumeUnit.LITRE.supportsArithmetic());
		assertTrue(VolumeUnit.MILLILITRE.supportsArithmetic());
	}

	@Test
	void testTemperatureUnitComparison() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> fahrenheit= new Quantity<>(212.0, TemperatureUnit.FAHRENHEIT);

		assertTrue(celsius.equals(fahrenheit));
	}

	@Test
	void testTemperatureUnitConversion() {

		Quantity<TemperatureUnit> celsius= new Quantity<>(100.0, TemperatureUnit.CELSIUS);

		Quantity<TemperatureUnit> converted= celsius.convertTo(TemperatureUnit.FAHRENHEIT);

		assertEquals(212.0, converted.getValue(), 0.0001);
	}

	@Test
	void testTemperatureUnitUnsupportedAddition() {

		Quantity<TemperatureUnit> celsius1= new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> celsius2= new Quantity<>(50.0, TemperatureUnit.CELSIUS);

		assertThrows(UnsupportedOperationException.class, () -> celsius1.add(celsius2));
	}

	@Test
	void serviceLengthFeetEqualsInches() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertTrue(service.compare(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
		));
	}

	@Test
	void serviceLengthYardsEqualsFeet() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertTrue(service.compare(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.YARDS),
			new QuantityDTO(3.0, QuantityDTO.LengthUnit.FEET)
		));
	}

	@Test
	void serviceConvertLengthFeetToInches() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.convert(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			QuantityDTO.LengthUnit.INCHES
		);

		assertEquals(12.0, result.getValue(), 0.0001);
	}

	@Test
	void serviceConvertLengthYardsToInches() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.convert(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.YARDS),
			QuantityDTO.LengthUnit.INCHES
		);

		assertEquals(36.0, result.getValue(), 0.0001);
	}

	@Test
	void serviceAddLengthFeetAndInches() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.add(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
		);

		assertEquals(2.0, result.getValue(), 0.0001);
	}

	@Test
	void serviceWeightKilogramEqualsGrams() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertTrue(service.compare(
			new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
			new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM)
		));
	}

	@Test
	void serviceWeightPoundEqualsGrams() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertTrue(service.compare(
			new QuantityDTO(1.0, QuantityDTO.WeightUnit.POUND),
			new QuantityDTO(453.592, QuantityDTO.WeightUnit.GRAM)
		));
	}

	@Test
	void serviceConvertWeightKilogramToGrams() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.convert(
			new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
			QuantityDTO.WeightUnit.GRAM
		);

		assertEquals(1000.0, result.getValue(), 0.0001);
	}

	@Test
	void serviceSubtractWeightKilogramAndGrams() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.subtract(
			new QuantityDTO(2.0, QuantityDTO.WeightUnit.KILOGRAM),
			new QuantityDTO(500.0, QuantityDTO.WeightUnit.GRAM)
		);

		assertEquals(1.5, result.getValue(), 0.0001);
	}

	@Test
	void serviceSubtractWeightWithExplicitTargetUnit() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.subtract(
			new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
			new QuantityDTO(250.0, QuantityDTO.WeightUnit.GRAM),
			QuantityDTO.WeightUnit.GRAM
		);

		assertEquals(750.0, result.getValue(), 0.0001);
	}

	@Test
	void serviceDivideWeightKilogramByKilogram() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		double result= service.divide(
			new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM),
			new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM)
		);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	void serviceVolumeLitreEqualsMillilitres() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertTrue(service.compare(
			new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE),
			new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE)
		));
	}

	@Test
	void serviceConvertVolumeGallonToMillilitres() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.convert(
			new QuantityDTO(1.0, QuantityDTO.VolumeUnit.GALLON),
			QuantityDTO.VolumeUnit.MILLILITRE
		);

		assertEquals(3785.41, result.getValue(), 0.01);
	}

	@Test
	void serviceAddVolumeLitreAndMillilitres() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.add(
			new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE),
			new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE)
		);

		assertEquals(2.0, result.getValue(), 0.0001);
	}

	@Test
	void serviceTemperatureCelsiusEqualsFahrenheit() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertTrue(service.compare(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT)
		));
	}

	@Test
	void serviceConvertTemperatureCelsiusToFahrenheit() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.convert(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			QuantityDTO.TemperatureUnit.FAHRENHEIT
		);

		assertEquals(212.0, result.getValue(), 0.0001);
	}

	@Test
	void serviceConvertTemperatureFahrenheitToCelsius() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		QuantityDTO result= service.convert(
			new QuantityDTO(32.0, QuantityDTO.TemperatureUnit.FAHRENHEIT),
			QuantityDTO.TemperatureUnit.CELSIUS
		);

		assertEquals(0.0, result.getValue(), 0.0001);
	}

	@Test
	void serviceTemperatureAdditionThrowsException() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertThrows(QuantityMeasurementException.class, () -> service.add(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS)
		));
	}

	@Test
	void serviceTemperatureDivisionThrowsException() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertThrows(QuantityMeasurementException.class, () -> service.divide(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS)
		));
	}

	@Test
	void servicePreventCrossTypeComparisonLengthVsWeight() {

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance());

		assertThrows(QuantityMeasurementException.class, () -> service.compare(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM)
		));
	}

	@Test
	void controllerLengthFeetEqualsInches() {

		QuantityMeasurementController controller= new QuantityMeasurementController(
			new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance())
		);

		assertTrue(controller.performComparison(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
		));
	}

	@Test
	void controllerConvertWeightKilogramToGrams() {

		QuantityMeasurementController controller= new QuantityMeasurementController(
			new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance())
		);

		QuantityDTO result= controller.performConversion(
			new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
			QuantityDTO.WeightUnit.GRAM
		);

		assertEquals(1000.0, result.getValue(), 0.0001);
	}

	@Test
	void controllerAddLengthFeetAndInches() {

		QuantityMeasurementController controller= new QuantityMeasurementController(
			new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance())
		);

		QuantityDTO result= controller.performAddition(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
		);

		assertEquals(2.0, result.getValue(), 0.0001);
	}

	@Test
	void controllerSubtractVolumeWithTargetUnit() {

		QuantityMeasurementController controller= new QuantityMeasurementController(
			new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance())
		);

		QuantityDTO result= controller.performSubtraction(
			new QuantityDTO(2.0, QuantityDTO.VolumeUnit.LITRE),
			new QuantityDTO(500.0, QuantityDTO.VolumeUnit.MILLILITRE),
			QuantityDTO.VolumeUnit.MILLILITRE
		);

		assertEquals(1500.0, result.getValue(), 0.0001);
	}

	@Test
	void controllerDivideWeightKilogramByKilogram() {

		QuantityMeasurementController controller= new QuantityMeasurementController(
			new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance())
		);

		double result= controller.performDivision(
			new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM),
			new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM)
		);

		assertEquals(2.0, result, 0.0001);
	}

	@Test
	void controllerTemperatureCelsiusEqualsFahrenheit() {

		QuantityMeasurementController controller= new QuantityMeasurementController(
			new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance())
		);

		assertTrue(controller.performComparison(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT)
		));
	}

	@Test
	void controllerConvertTemperatureCelsiusToFahrenheit() {

		QuantityMeasurementController controller= new QuantityMeasurementController(
			new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance())
		);

		QuantityDTO result= controller.performConversion(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			QuantityDTO.TemperatureUnit.FAHRENHEIT
		);

		assertEquals(212.0, result.getValue(), 0.0001);
	}

	@Test
	void controllerTemperatureAdditionThrowsException() {

		QuantityMeasurementController controller= new QuantityMeasurementController(
			new QuantityMeasurementServiceImplementation(QuantityMeasurementCacheRepository.getInstance())
		);

		assertThrows(QuantityMeasurementException.class, () -> controller.performAddition(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS)
		));
	}
}