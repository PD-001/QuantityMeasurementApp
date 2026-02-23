package com.apps.quantitymeasurement;

public enum LengthUnit {

    INCHES(1.0),
    FEET(12.0),
    YARDS(36.0),
    CENTIMETERS(0.393701);

    private final double toBaseFactor;

    LengthUnit(double toBaseFactor) {
        this.toBaseFactor= toBaseFactor;
    }

    public double convertToBaseUnit(double value) {
        return value*toBaseFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue/toBaseFactor;
    }
}