package com.apps.quantitymeasurement;

import java.util.Objects;

public class Length {
	
	private final double value;
	private final LengthUnit unit;
	
	public enum LengthUnit{
		FEET(12.0),
		INCHES(1.0),
		YARDS(36.0),
		CENTIMETERS(0.393701);
		
		private final double conversionFactor;
		
		LengthUnit(double conversionFactor){
			this.conversionFactor= conversionFactor;
		}
		
		public double getConversion() {
			return conversionFactor;
		}
		
	}
	
	public Length(double value, LengthUnit unit) {
		if(value<0) {
			throw new IllegalArgumentException("Length cannot be negative");
		}
		if(unit==null) {
			throw new IllegalArgumentException("Unit cannot be null");
		}
		
		this.value= value;
		this.unit= unit;
	}
	
	public double convertToBaseUnit() {
		return this.value*this.unit.conversionFactor;
	}
	
	public boolean compare(Length thatLength) {
		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit())==0;
	}
	
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		
		if(o==null || this.getClass()!=o.getClass()) return false;
		
		Length obj=(Length) o;
		
		return this.compare(obj);
	}
	
	@Override
	public int hashCode() {
		return Double.hashCode(this.convertToBaseUnit());
	}
	
	@Override
	public String toString() {
		return value+":"+unit;
	}
	
	public static void main(String[] args) {
		Length l1= new Length(1.0, LengthUnit.FEET);
		Length l2= new Length(12.0, LengthUnit.INCHES);
		System.out.println("Equal? "+l1.equals(l2));
	}
	
}
