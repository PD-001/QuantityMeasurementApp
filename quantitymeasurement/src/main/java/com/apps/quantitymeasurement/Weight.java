package com.apps.quantitymeasurement;

public class Weight {

    private final double value;
    private final WeightUnit unit;
    private static final double epsilon = 0.0001;

    public Weight(double value, WeightUnit unit) {
        if (!Double.isFinite(value) || value<0) throw new IllegalArgumentException("Weight must be finite and non-negative");

        if (unit==null) throw new IllegalArgumentException("Unit cannot be null");

        this.value= value;
        this.unit= unit;
    }

    private double toBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public Weight convertTo(WeightUnit targetUnit) {
        if (targetUnit==null) throw new IllegalArgumentException("Target unit cannot be null");

        double base= this.toBaseUnit();
        double converted= targetUnit.convertFromBaseUnit(base);

        return new Weight(converted, targetUnit);
    }

    public static double convert(double value, WeightUnit source, WeightUnit target) {

        if (!Double.isFinite(value) || value<0) throw new IllegalArgumentException("Value must be finite and non-negative");

        if (source==null || target==null) throw new IllegalArgumentException("Units cannot be null");

        double base= source.convertToBaseUnit(value);
        return target.convertFromBaseUnit(base);
    }

    public Weight add(Weight other) {
        if (other==null) throw new IllegalArgumentException("Weight cannot be null");

        double baseSum= this.toBaseUnit()+other.toBaseUnit();
        double result= this.unit.convertFromBaseUnit(baseSum);

        return new Weight(result, this.unit);
    }

    public static Weight add(Weight w1, Weight w2, WeightUnit targetUnit) {

        if (w1==null || w2==null) throw new IllegalArgumentException("Weights cannot be null");

        if (targetUnit==null) throw new IllegalArgumentException("Target unit cannot be null");

        double baseSum= w1.toBaseUnit()+w2.toBaseUnit();
        double converted= targetUnit.convertFromBaseUnit(baseSum);

        return new Weight(converted, targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this==obj) return true;
        if (obj==null || getClass()!=obj.getClass()) return false;

        Weight other = (Weight) obj;

        return Math.abs(this.toBaseUnit()-other.toBaseUnit())<epsilon;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(toBaseUnit());
    }

    @Override
    public String toString() {
        return value + ":" + unit;
    }

    public double getValue() {
        return value;
    }

    public WeightUnit getUnit() {
        return unit;
    }
}