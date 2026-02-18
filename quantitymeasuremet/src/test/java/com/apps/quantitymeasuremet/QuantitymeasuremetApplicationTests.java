package com.apps.quantitymeasuremet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.apps.quantitymeasuremet.QuantitymeasuremetApplication.Feet;

@SpringBootTest
class QuantitymeasuremetApplicationTests {

	@Test
	void testEquality_SameValue() {
		Feet f1= new Feet(1.0);
		Feet f2= new Feet(1.0);
		
		assertEquals(true, f1.equals(f2));
	}
	
	@Test
	void testEquality_DifferentValue() {
		Feet f1= new Feet(1.0);
		Feet f2= new Feet(2.0);
		
		assertEquals(false, f1.equals(f2));
	}
	
	@Test
	void testEquality_NullComparision() {
		Feet f1= new Feet(20);
		Feet f2= null;
		
		assertEquals(false, f1.equals(f2));
	}
	
	@Test
	void testEquality_NonNumericalInput() {
		Feet f1= new Feet(20);
		
		assertEquals(false, f1.equals("l"));
	}
	
	@Test
	void testEquality_SameReference() {
		Feet f1= new Feet(10);
		
		assertEquals(true, f1.equals(f1));
	}

}
