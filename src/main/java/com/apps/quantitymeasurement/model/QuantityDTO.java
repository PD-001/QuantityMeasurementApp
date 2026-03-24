package com.apps.quantitymeasurement.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.apps.quantitymeasurement.model.QuantityDTO.IMeasurableUnitDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class QuantityDTO {

    //Unit interface

    @JsonDeserialize(using= IMeasurableUnitDeserializer.class) 
    public interface IMeasurableUnit {
        String getUnitName();
        String getMeasurementType();
    }

    //Custom deserializer — searches all enums for the unit name
    public static class IMeasurableUnitDeserializer extends JsonDeserializer<IMeasurableUnit> {
        @Override
        public IMeasurableUnit deserialize(JsonParser p, DeserializationContext ctx)
                throws IOException {
            String name = p.getText().toUpperCase();
            // Try each enum in order
            for (LengthUnit u : LengthUnit.values()){ if (u.name().equals(name)) return u; }
            for (WeightUnit u : WeightUnit.values()){ if (u.name().equals(name)) return u; }
            for (VolumeUnit u : VolumeUnit.values()){ if (u.name().equals(name)) return u; }
            for (TemperatureUnit u : TemperatureUnit.values()){ if (u.name().equals(name)) return u; }
            throw new IllegalArgumentException("Unknown unit: " + name
                + ". Valid values: INCHES, FEET, YARDS, CENTIMETERS, "
                + "GRAM, KILOGRAM, POUND, LITRE, MILLILITRE, GALLON, CELSIUS, FAHRENHEIT");
        }
    }

    //Enums

    public enum LengthUnit implements IMeasurableUnit { INCHES, FEET, YARDS, CENTIMETERS;
        @Override public String getUnitName(){ return this.name(); }
        @Override public String getMeasurementType(){ return "LENGTH"; }
    }

    public enum WeightUnit implements IMeasurableUnit { GRAM, KILOGRAM, POUND;
        @Override public String getUnitName(){ return this.name(); }
        @Override public String getMeasurementType(){ return "WEIGHT"; }
    }

    public enum VolumeUnit implements IMeasurableUnit { LITRE, MILLILITRE, GALLON;
        @Override public String getUnitName(){ return this.name(); }
        @Override public String getMeasurementType(){ return "VOLUME"; }
    }

    public enum TemperatureUnit implements IMeasurableUnit { CELSIUS, FAHRENHEIT;
        @Override public String getUnitName(){ return this.name(); }
        @Override public String getMeasurementType(){ return "TEMPERATURE"; }
    }

    //Fields

    private final double value;
    private final IMeasurableUnit unit;

    @JsonCreator
    public QuantityDTO(
            @JsonProperty("value") double value,
            @JsonProperty("unit")  IMeasurableUnit unit) {

        if (!Double.isFinite(value)) throw new IllegalArgumentException("Value must be finite");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");

        this.value= value;
        this.unit= unit;
    }

    public double getValue(){ return value; }
    public IMeasurableUnit getUnit(){ return unit; }

    @Override
    public String toString() { return value + ":" + unit.getUnitName(); }
}