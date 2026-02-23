package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.Length.LengthUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class QuantityMeasurementApplicationTests {

    @Test
    public void testConversion_FeetToInches() {
        assertEquals(12.0, Length.convert(1.0, LengthUnit.FEET, LengthUnit.INCHES));
    }

    @Test
    public void testConversion_InchesToFeet() {
        assertEquals(2.0, Length.convert(24.0, LengthUnit.INCHES, LengthUnit.FEET));
    }

    @Test
    public void testConversion_YardsToInches() {
        assertEquals(36.0, Length.convert(1.0, LengthUnit.YARDS, LengthUnit.INCHES));
    }

    @Test
    public void testConversion_InchesToYards() {
        assertEquals(2.0, Length.convert(72.0, LengthUnit.INCHES, LengthUnit.YARDS));
    }

    @Test
    public void testConversion_FeetToYards() {
        assertEquals(2.0, Length.convert(6.0, LengthUnit.FEET, LengthUnit.YARDS));
    }

    @Test
    public void testConversion_YardsToFeet() {
        assertEquals(9.0, Length.convert(3.0, LengthUnit.YARDS, LengthUnit.FEET));
    }


    @Test
    public void testConversion_CentimetersToInches() {
        assertEquals(1.0, Length.convert(2.54, LengthUnit.CENTIMETERS, LengthUnit.INCHES),0.001);
    }

    @Test
    public void testConversion_InchesToCentimeters() {
        assertEquals(2.54, Length.convert(1.0, LengthUnit.INCHES, LengthUnit.CENTIMETERS), 0.001);
    }

    @Test
    public void testConversion_CentimetersToFeet() {
        assertEquals(1.0, Length.convert(30.48, LengthUnit.CENTIMETERS, LengthUnit.FEET),0.0001);
    }

    @Test
    public void testConversion_FeetToCentimeters() {
        assertEquals(30.48, Length.convert(1.0, LengthUnit.FEET, LengthUnit.CENTIMETERS),0.0001);
    }


    @Test
    public void testConversion_SameUnit_Feet() {
        assertEquals(5.0, Length.convert(5.0, LengthUnit.FEET, LengthUnit.FEET));
    }

    @Test
    public void testConversion_SameUnit_Inches() {
        assertEquals(7.0, Length.convert(7.0, LengthUnit.INCHES, LengthUnit.INCHES));
    }

    @Test
    public void testConversion_SameUnit_Yards() {
        assertEquals(3.0, Length.convert(3.0, LengthUnit.YARDS, LengthUnit.YARDS));
    }

    @Test
    public void testConversion_SameUnit_Centimeters() {
        assertEquals(100.0, Length.convert(100.0, LengthUnit.CENTIMETERS, LengthUnit.CENTIMETERS));
    }


    @Test
    public void testConversion_ZeroValue() {
        assertEquals(0.0, Length.convert(0.0, LengthUnit.FEET, LengthUnit.INCHES));
    }

    @Test
    public void testConversion_NegativeValue() {
        assertThrows(IllegalArgumentException.class, ()->Length.convert(-1.0, LengthUnit.FEET, LengthUnit.INCHES));
    }

 

    @Test
    public void testConversion_RoundTrip_FeetInches() {
        double original= 5.0;
        double toInches= Length.convert(original, LengthUnit.FEET, LengthUnit.INCHES);
        double backToFeet= Length.convert(toInches, LengthUnit.INCHES, LengthUnit.FEET);
        assertEquals(original, backToFeet);
    }

    @Test
    public void testConversion_RoundTrip_YardsCentimeters() {
        double original= 2.0;
        double toCm = Length.convert(original, LengthUnit.YARDS, LengthUnit.CENTIMETERS);
        double backToYards= Length.convert(toCm, LengthUnit.CENTIMETERS, LengthUnit.YARDS);
        assertEquals(original, backToYards);
    }

    @Test
    public void testConversion_MultiStep_RoundTrip() {
        double original= 3.0;
        double step1= Length.convert(original, LengthUnit.FEET, LengthUnit.INCHES);
        double step2= Length.convert(step1, LengthUnit.INCHES, LengthUnit.YARDS);
        double step3= Length.convert(step2, LengthUnit.YARDS, LengthUnit.FEET);
        assertEquals(original, step3);
    }



    @Test
    public void testInstanceMethod_ConvertTo_FeetToInches() {
        Length feet= new Length(1.0, LengthUnit.FEET);
        Length inches= feet.convertTo(LengthUnit.INCHES);
        assertEquals(12.0, inches.getValue());
    }

    @Test
    public void testInstanceMethod_ConvertTo_YardsToFeet() {
        Length yards= new Length(1.0, LengthUnit.YARDS);
        Length feet= yards.convertTo(LengthUnit.FEET);
        assertEquals(3.0, feet.getValue());
    }

    @Test
    public void testInstanceMethod_ConvertTo_ReturnsNewInstance() {
        Length original= new Length(1.0, LengthUnit.FEET);
        Length converted= original.convertTo(LengthUnit.INCHES);
        assertNotSame(original.getValue(), converted);
    }

 

    @Test
    public void testDemonstrateLengthConversion_RawValues() {
        double result= QuantityMeasurementApplication.demonstrateLengthConversion(3.0, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(36.0, result);
    }

    @Test
    public void testDemonstrateLengthConversion_FromInstance() {
        Length yards= new Length(1.0, LengthUnit.YARDS);
        Length result= QuantityMeasurementApplication.demonstrateLengthConversion(yards, LengthUnit.INCHES);
        assertEquals(36.0, result.getValue());
    }


    @Test
    public void testConversion_NullSourceUnit_Throws() {
        assertThrows(IllegalArgumentException.class, ()->Length.convert(1.0, null, LengthUnit.INCHES));
    }

    @Test
    public void testConversion_NullTargetUnit_Throws() {
        assertThrows(IllegalArgumentException.class, ()->Length.convert(1.0, LengthUnit.FEET, null));
    }

    @Test
    public void testConversion_NaN_Throws() {
        assertThrows(IllegalArgumentException.class, ()->Length.convert(Double.NaN, LengthUnit.FEET, LengthUnit.INCHES));
    }

    @Test
    public void testConversion_PositiveInfinity_Throws() {
        assertThrows(IllegalArgumentException.class, ()->Length.convert(Double.POSITIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCHES));
    }

    @Test
    public void testConversion_NegativeInfinity_Throws() {
        assertThrows(IllegalArgumentException.class, ()->Length.convert(Double.NEGATIVE_INFINITY, LengthUnit.FEET, LengthUnit.INCHES));
    }

    @Test
    public void testConstructor_NullUnit_Throws() {
        assertThrows(IllegalArgumentException.class, ()->new Length(1.0, null));
    }

    @Test
    public void testConvertTo_NullTargetUnit_Throws() {
        Length length= new Length(1.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, ()->length.convertTo(null));
    }


    @Test
    public void testToString_ContainsUnitName() {
        Length length= new Length(3.0, LengthUnit.FEET);
        String s= length.toString();
        assertTrue(s.contains("FEET"));
    }

   

    @Test
    public void testConversion_LargeValue() {
        double result= Length.convert(1_000_000.0, LengthUnit.FEET, LengthUnit.INCHES);
        assertEquals(12_000_000.0, result, 1.0);  
    }

    @Test
    public void testConversion_SmallValue_Precision() {
        assertEquals(0.12, Length.convert(0.01, LengthUnit.FEET, LengthUnit.INCHES));
    }



    @Test
    public void testMathematicalConsistency_Formula() {
        double value= 5.0;
        double expected= value*(LengthUnit.YARDS.getConversionFactor()/LengthUnit.FEET.getConversionFactor());
        assertEquals(expected, Length.convert(value, LengthUnit.YARDS, LengthUnit.FEET));
    }
    
    @Test
    public void addFeetAndInches(){
    	Length length1= new Length(1.0, Length.LengthUnit.FEET);
    	Length length2= new Length(12.0, Length.LengthUnit.INCHES);
    	
    	ArrayList<Length> sumLength= QuantityMeasurementApplication.demonstrateLengthAddition(length1, length2, Length.LengthUnit.CENTIMETERS);
    	Length expectedLength = new Length(2.0, Length. LengthUnit.FEET);
    	
    	assertTrue(QuantityMeasurementApplication.demonstrateLengthEquality(sumLength.get(0), expectedLength));
    }
}