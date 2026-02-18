package com.apps.quantitymeasuremet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuantitymeasuremetApplication {
	
	public static class Feet{
		
		private final double measurement;
		
		public Feet(double measurement) {
			this.measurement= measurement;		
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

	public static void main(String[] args) {
//		SpringApplication.run(QuantitymeasuremetApplication.class, args);
			
		Feet f1= new Feet(20);
		Feet f2= new Feet(20);
		System.out.println("Is F1 equals to F2?: "+ f1.equals(f2));
		
	}

}
