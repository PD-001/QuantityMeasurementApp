package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementApplicationTests {

	@Test
	public void testEquality_YardToYard_SameValue() {

		Length l1= new Length(1.0, Length.LengthUnit.YARDS);
		Length l2= new Length(1.0, Length.LengthUnit.YARDS);

		boolean result= l1.equals(l2);

		assertTrue(result);
	}

	@Test
	public void testEquality_YardToYard_DifferentValue() {

		Length l1= new Length(1.0, Length.LengthUnit.YARDS);
		Length l2= new Length(2.0, Length.LengthUnit.YARDS);

		boolean result= l1.equals(l2);

		assertFalse(result);
	}

	@Test
	public void testEquality_YardToFeet_EquivalentValue() {

		Length l1= new Length(1.0, Length.LengthUnit.YARDS);
		Length l2= new Length(3.0, Length.LengthUnit.FEET);

		boolean result= l1.equals(l2);

		assertTrue(result);
	}

	@Test
	public void testEquality_FeetToYard_EquivalentValue() {

		Length l1= new Length(3.0, Length.LengthUnit.FEET);
		Length l2= new Length(1.0, Length.LengthUnit.YARDS);

		boolean result= l1.equals(l2);

		assertTrue(result);
	}

	@Test
	public void testEquality_YardToInches_EquivalentValue() {

		Length l1= new Length(1.0, Length.LengthUnit.YARDS);
		Length l2= new Length(36.0, Length.LengthUnit.INCHES);

		boolean result= l1.equals(l2);

		assertTrue(result);
	}

	@Test
	public void testEquality_InchesToYard_EquivalentValue() {

		Length l1= new Length(36.0, Length.LengthUnit.INCHES);
		Length l2= new Length(1.0, Length.LengthUnit.YARDS);

		boolean result= l1.equals(l2);

		assertTrue(result);
	}

	@Test
	public void testEquality_YardToFeet_NonEquivalentValue() {

		Length l1= new Length(1.0, Length.LengthUnit.YARDS);
		Length l2= new Length(2.0, Length.LengthUnit.FEET);

		boolean result= l1.equals(l2);

		assertFalse(result);
	}

	@Test
	public void testEquality_centimetersToInches_EquivalentValue() {

		Length l1= new Length(1.0, Length.LengthUnit.CENTIMETERS);
		Length l2= new Length(0.393701, Length.LengthUnit.INCHES);

		boolean result= l1.equals(l2);

		assertTrue(result);
	}

	@Test
	public void testEquality_centimetersToFeet_NonEquivalentValue() {

		Length l1= new Length(1.0, Length.LengthUnit.CENTIMETERS);
		Length l2= new Length(1.0, Length.LengthUnit.FEET);

		boolean result= l1.equals(l2);

		assertFalse(result);
	}

	@Test
	public void testEquality_MultiUnit_TransitiveProperty() {

		Length yard= new Length(1.0, Length.LengthUnit.YARDS);
		Length feet= new Length(3.0, Length.LengthUnit.FEET);
		Length inches= new Length(36.0, Length.LengthUnit.INCHES);

		boolean firstComparison= yard.equals(feet);
		boolean secondComparison= feet.equals(inches);
		boolean finalComparison= yard.equals(inches);

		assertTrue(firstComparison);
		assertTrue(secondComparison);
		assertTrue(finalComparison);
	}

	@Test
	public void testEquality_YardWithNullUnit() {

		assertThrows(IllegalArgumentException.class, ()->new Length(1.0, null));
		
	}

	@Test
	public void testEquality_YardSameReference() {

		Length l1= new Length(1.0, Length.LengthUnit.YARDS);

		boolean result= l1.equals(l1);

		assertTrue(result);
	}

	@Test
	public void testEquality_YardNullComparison() {

		Length l1= new Length(1.0, Length.LengthUnit.YARDS);

		boolean result= l1.equals(null);

		assertFalse(result);
	}

	@Test
	public void testEquality_CentimetersWithNullUnit() {

		assertThrows(IllegalArgumentException.class, ()->new Length(1.0, null));
		
	}

	@Test
	public void testEquality_CentimetersSameReference() {

		Length l1= new Length(1.0, Length.LengthUnit.CENTIMETERS);

		boolean result= l1.equals(l1);

		assertTrue(result);
	}

	@Test
	public void testEquality_CentimetersNullComparison() {

		Length l1= new Length(1.0, Length.LengthUnit.CENTIMETERS);

		boolean result= l1.equals(null);

		assertFalse(result);
	}

	@Test
	public void testEquality_AllUnits_ComplexScenario() {

		Length yard= new Length(2.0, Length.LengthUnit.YARDS);
		Length feet= new Length(6.0, Length.LengthUnit.FEET);
		Length inches= new Length(72.0, Length.LengthUnit.INCHES);

		boolean firstComparison= yard.equals(feet);
		boolean secondComparison= feet.equals(inches);
		boolean finalComparison= yard.equals(inches);

		assertTrue(firstComparison);
		assertTrue(secondComparison);
		assertTrue(finalComparison);
	}
}
