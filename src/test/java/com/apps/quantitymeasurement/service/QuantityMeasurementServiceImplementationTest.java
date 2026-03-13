package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuantityMeasurementServiceImplementationTest {

    @Mock
    private IQuantityMeasurementRepository repository;

    private QuantityMeasurementServiceImplementation service;

    @BeforeEach
    void setUp() {
        service = new QuantityMeasurementServiceImplementation(repository);
    }

    // ── compare ───────────────────────────────────────────────────────────────

    @Test
    void compare_equalLengths_returnsTrue() {
        QuantityDTO q1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        boolean result = service.compare(q1, q2);

        assertTrue(result);
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void compare_unequalLengths_returnsFalse() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(5.0, QuantityDTO.LengthUnit.INCHES);

        boolean result = service.compare(q1, q2);

        assertFalse(result);
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void compare_equalWeights_returnsTrue() {
        QuantityDTO q1 = new QuantityDTO(1.0,    QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM);

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void compare_equalVolumes_returnsTrue() {
        QuantityDTO q1 = new QuantityDTO(1.0,    QuantityDTO.VolumeUnit.LITRE);
        QuantityDTO q2 = new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE);

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void compare_equalTemperatures_returnsTrue() {
        QuantityDTO q1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO q2 = new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);

        assertTrue(service.compare(q1, q2));
    }

    @Test
    void compare_differentMeasurementTypes_throwsQuantityMeasurementException() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);

        assertThrows(QuantityMeasurementException.class, () -> service.compare(q1, q2));
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    // ── convert ───────────────────────────────────────────────────────────────

    @Test
    void convert_feetToInches_returnsCorrectDTO() {
        QuantityDTO quantity = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);

        QuantityDTO result = service.convert(quantity, QuantityDTO.LengthUnit.INCHES);

        assertNotNull(result);
        assertEquals(12.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.LengthUnit.INCHES, result.getUnit());
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void convert_kilogramToGram_returnsCorrectDTO() {
        QuantityDTO quantity = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);

        QuantityDTO result = service.convert(quantity, QuantityDTO.WeightUnit.GRAM);

        assertEquals(1000.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.WeightUnit.GRAM, result.getUnit());
    }

    @Test
    void convert_celsiusToFahrenheit_returnsCorrectDTO() {
        QuantityDTO quantity = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);

        QuantityDTO result = service.convert(quantity, QuantityDTO.TemperatureUnit.FAHRENHEIT);

        assertEquals(212.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.TemperatureUnit.FAHRENHEIT, result.getUnit());
    }

    @Test
    void convert_litreToMillilitre_returnsCorrectDTO() {
        QuantityDTO quantity = new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE);

        QuantityDTO result = service.convert(quantity, QuantityDTO.VolumeUnit.MILLILITRE);

        assertEquals(1000.0, result.getValue(), 0.01);
    }

    @Test
    void convert_inchesToYards_returnsCorrectDTO() {
        QuantityDTO quantity = new QuantityDTO(36.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result = service.convert(quantity, QuantityDTO.LengthUnit.YARDS);

        assertEquals(1.0, result.getValue(), 0.01);
    }

    // ── add ───────────────────────────────────────────────────────────────────

    @Test
    void add_feetAndInches_returnsResultInFeet() {
        QuantityDTO q1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result = service.add(q1, q2);

        assertNotNull(result);
        assertEquals(2.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.LengthUnit.FEET, result.getUnit());
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void add_gramsAndKilograms_returnsResultInGrams() {
        QuantityDTO q1 = new QuantityDTO(250.0, QuantityDTO.WeightUnit.GRAM);
        QuantityDTO q2 = new QuantityDTO(0.5,   QuantityDTO.WeightUnit.KILOGRAM);

        QuantityDTO result = service.add(q1, q2);

        assertEquals(750.0, result.getValue(), 0.01);
    }

    @Test
    void add_withTargetUnit_feetAndInchesToInches() {
        QuantityDTO q1 = new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result = service.add(q1, q2, QuantityDTO.LengthUnit.INCHES);

        assertEquals(24.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.LengthUnit.INCHES, result.getUnit());
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void add_differentMeasurementTypes_throwsQuantityMeasurementException() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);

        assertThrows(QuantityMeasurementException.class, () -> service.add(q1, q2));
    }

    @Test
    void add_temperatureUnits_throwsUnsupportedOperationException() {
        QuantityDTO q1 = new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
        QuantityDTO q2 = new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS);

        assertThrows(QuantityMeasurementException.class, () -> service.add(q1, q2));
    }

    // ── subtract ─────────────────────────────────────────────────────────────

    @Test
    void subtract_kilogramsMinusGrams_returnsCorrectResult() {
        QuantityDTO q1 = new QuantityDTO(2.0,   QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(500.0, QuantityDTO.WeightUnit.GRAM);

        QuantityDTO result = service.subtract(q1, q2);

        assertNotNull(result);
        assertEquals(1.5, result.getValue(), 0.01);
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void subtract_feetMinusInches_returnsResultInFeet() {
        QuantityDTO q1 = new QuantityDTO(2.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        QuantityDTO result = service.subtract(q1, q2);

        assertEquals(1.0, result.getValue(), 0.01);
    }

    @Test
    void subtract_withTargetUnit_returnsResultInTargetUnit() {
        QuantityDTO q1 = new QuantityDTO(2.0,   QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(500.0, QuantityDTO.WeightUnit.GRAM);

        QuantityDTO result = service.subtract(q1, q2, QuantityDTO.WeightUnit.GRAM);

        assertEquals(1500.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.WeightUnit.GRAM, result.getUnit());
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void subtract_differentMeasurementTypes_throwsQuantityMeasurementException() {
        QuantityDTO q1 = new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE);

        assertThrows(QuantityMeasurementException.class, () -> service.subtract(q1, q2));
    }

    // ── divide ────────────────────────────────────────────────────────────────

    @Test
    void divide_tenKgByFiveKg_returnsTwo() {
        QuantityDTO q1 = new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(5.0,  QuantityDTO.WeightUnit.KILOGRAM);

        double result = service.divide(q1, q2);

        assertEquals(2.0, result, 0.001);
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }

    @Test
    void divide_feetByInches_returnsScalarRatio() {
        QuantityDTO q1 = new QuantityDTO(2.0,  QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);

        double result = service.divide(q1, q2);

        assertEquals(2.0, result, 0.001);
    }

    @Test
    void divide_byZero_throwsException() {
        QuantityDTO q1 = new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM);
        QuantityDTO q2 = new QuantityDTO(0.0,  QuantityDTO.WeightUnit.KILOGRAM);

        assertThrows(QuantityMeasurementException.class, () -> service.divide(q1, q2));
    }

    @Test
    void divide_differentMeasurementTypes_throwsQuantityMeasurementException() {
        QuantityDTO q1 = new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
        QuantityDTO q2 = new QuantityDTO(5.0,  QuantityDTO.WeightUnit.KILOGRAM);

        assertThrows(QuantityMeasurementException.class, () -> service.divide(q1, q2));
    }

    // ── repository interaction ────────────────────────────────────────────────

    @Test
    void anyOperation_repositorySaveCalledExactlyOnce() {
        service.compare(
            new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
        );
        verify(repository, times(1)).save(any(QuantityMeasurementEntity.class));
    }
}