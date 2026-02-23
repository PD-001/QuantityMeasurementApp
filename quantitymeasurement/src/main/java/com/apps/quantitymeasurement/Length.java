package com.apps.quantitymeasurement;

public class Length {
	
	private final double value;
	private final LengthUnit unit;
	private static final double Epsilon= 0.0001d;
	
	public enum LengthUnit{
		FEET(12.0),
		INCHES(1.0),
		YARDS(36.0),
		CENTIMETERS(0.393701);
		
		private final double conversionFactor;
		
		LengthUnit(double conversionFactor){
			this.conversionFactor= conversionFactor;
		}
		
		public double getConversionFactor() {
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
//		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit())==0;
		
		return Math.abs(this.convertToBaseUnit()-thatLength.convertToBaseUnit())< Epsilon;
	}
	
	public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
		
		if(!Double.isFinite(value) || value<0) throw new IllegalArgumentException("Value must be finite and positive");
		
		if(sourceUnit==null || targetUnit==null) throw new IllegalArgumentException("Unit cannot be null");
		
		double base= value*sourceUnit.getConversionFactor();
		
		double converted= base/targetUnit.getConversionFactor();
		
		return converted;
		
	}
	
	public double convertTo(LengthUnit unit) {
		if(unit==null) throw new IllegalArgumentException("Unit cannot be null");
		
		return convert(this.value,this.unit, unit);
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
	
	public LengthUnit getUnit() {
		
		return unit;
	}
	
	public double getValue() {
		return value;
	}
	
	public static void main(String[] args) {
		Length l1= new Length(1.0, LengthUnit.FEET);
		Length l2= new Length(12.0, LengthUnit.INCHES);
		System.out.println("Equal? "+l1.equals(l2));
	}
	
}
