package com.apps.quantitymeasuremet;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.apps.quantitymeasuremet.QuantitymeasuremetApplication.Feet;
import com.apps.quantitymeasuremet.QuantitymeasuremetApplication.Inches;


@SpringBootTest
class QuantitymeasuremetApplicationTests {

	@Test
	void testEquality_SameValue_F() {
		Feet f1= new Feet(1.0);
		Feet f2= new Feet(1.0);
		
		assertEquals(true, f1.equals(f2));
	}
	
	@Test
	void testEquality_DifferentValue_F() {
		Feet f1= new Feet(1.0);
		Feet f2= new Feet(2.0);
		
		assertEquals(false, f1.equals(f2));
	}
	
	@Test
	void testEquality_NullComparision_F() {
		Feet f1= new Feet(20);
		Feet f2= null;
		
		assertEquals(false, f1.equals(f2));
	}
	
	@Test
	void testEquality_NonNumericalInput_F() {
		Feet f1= new Feet(20);
		
		assertEquals(false, f1.equals("l"));
	}
	
	@Test
	void testEquality_SameReference_F() {
		Feet f1= new Feet(10);
		
		assertEquals(true, f1.equals(f1));
	}
	
	@Test
	void testEquality_SameValue_I() {
		Inches i1= new Inches(10);
		Inches i2= new Inches(10);
		
		assertEquals(true, i1.equals(i2));
	}
	
	@Test
	void testEquality_DifferentValue_I() {
		Inches i1= new Inches(1.0);
		Inches i2= new Inches(2.0);
		
		assertEquals(false, i1.equals(i2));
	}
	
	@Test
	void testEquality_NullComparision_I() {
		Inches i1= new Inches(20);
		Inches i2= null;
		
		assertEquals(false, i1.equals(i2));
	}
	
	@Test
	void testEquality_NonNumericalInput_I() {
		Inches i1= new Inches(20);
		
		assertEquals(false, i1.equals("l"));
	}
	
	@Test
	void testEquality_SameReference_I() {
		Inches i1= new Inches(10);
		
		assertEquals(true, i1.equals(i1));
	}

}
