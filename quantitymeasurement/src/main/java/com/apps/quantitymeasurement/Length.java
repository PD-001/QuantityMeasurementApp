package com.apps.quantitymeasurement;

public class Length {
	
	private final double value;
	private final LengthUnit unit;
	private static final double Epsilon= 0.0001d;
	
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
	
	public double toBaseUnit() {
		return unit.convertToBaseUnit(value);
	}
	
	public boolean compare(Length thatLength) {
//		return Double.compare(this.convertToBaseUnit(), thatLength.convertToBaseUnit())==0;
		
		return Math.abs(this.toBaseUnit()-thatLength.toBaseUnit())< Epsilon;
	}
	
	public static double convert(double value, LengthUnit sourceUnit, LengthUnit targetUnit) {
		
		if(!Double.isFinite(value) || value<0) throw new IllegalArgumentException("Value must be finite and positive");
		
		if(sourceUnit==null || targetUnit==null) throw new IllegalArgumentException("Unit cannot be null");
		
		double base= sourceUnit.convertToBaseUnit(value);
		
		double converted= targetUnit.convertFromBaseUnit(base);
		
		return converted;
		
	}
	
	public Length convertTo(LengthUnit unit) {
		if(unit==null) throw new IllegalArgumentException("Unit cannot be null");
		
		return new Length(convert(this.value,this.unit, unit), unit);
	}
	
	public static Length add(Length l1, Length l2, LengthUnit targetUnit) {
		
		if(l1==null || l2==null) throw new IllegalArgumentException("Lengths cannot be null");
		
		if(targetUnit==null) throw new IllegalArgumentException("Unit cannot be null");
		
		if(!Double.isFinite(l1.value) || l1.value<0 || !Double.isFinite(l2.value) || l2.value<0) throw new IllegalArgumentException("Value must be finite and positive");
			
		double baseSum= l1.toBaseUnit()+l2.toBaseUnit();
	    double converted= targetUnit.convertFromBaseUnit(baseSum);

	    return new Length(converted, targetUnit);
	}
	
	public Length add(Length thatLength) {
		
		Length newLength= add(this, thatLength, this.unit);
		
		return newLength;
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
		return Double.hashCode(this.toBaseUnit());
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
