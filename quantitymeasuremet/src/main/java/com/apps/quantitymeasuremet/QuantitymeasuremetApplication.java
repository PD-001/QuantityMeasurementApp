package com.apps.quantitymeasuremet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuantitymeasuremetApplication {
	
	public static class Feet{
		
		private final double measurement;
		
		public Feet(double measurement) {
			validateInput(measurement);
			this.measurement= measurement;
		}
		
		private void validateInput(double measurement) {
		    if (Double.isNaN(measurement) || Double.isInfinite(measurement) || measurement<0)
		        throw new IllegalArgumentException("Invalid value");
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this==obj) {
				return true;
			}
			if(obj==null || this.getClass()!=obj.getClass()) {
				return false;
			}
			
			Feet object= (Feet) obj;
			
			return Double.compare(this.measurement, object.measurement)==0;
		}
		
		@Override
		public int hashCode() {
			return Double.hashCode(measurement);
		}
		
	}
	
	public static class Inches{
		private final double measurement;
		
		public Inches(double measurement) {
			validateInput(measurement);
			this.measurement= measurement;
		}
		
		private void validateInput(double measurement) {
		    if (Double.isNaN(measurement) || Double.isInfinite(measurement) || measurement<0)
		        throw new IllegalArgumentException("Invalid value");
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this==obj) {
				return true;
			}
			if(obj==null || this.getClass()!=obj.getClass()) {
				return false;
			}
			
			Inches object= (Inches) obj;
			
			return Double.compare(this.measurement, object.measurement)==0;
		}
		
		@Override
		public int hashCode() {
			return Double.hashCode(measurement);
		}
	}
	
    public static boolean checkFeetEquality(double m1, double m2) {
        Feet f1= new Feet(m1);
        Feet f2= new Feet(m2);
        return f1.equals(f2);
    }

    public static boolean checkInchesEquality(double m1, double m2) {
        Inches i1= new Inches(m1);
        Inches i2= new Inches(m2);
        return i1.equals(i2);
    }


	public static void main(String[] args) {
//		SpringApplication.run(QuantitymeasuremetApplication.class, args);
			
		System.out.println("Feet Equal? " + checkFeetEquality(20, 20));
        System.out.println("Inches Equal? " + checkInchesEquality(12, 12));
		
	}

}
