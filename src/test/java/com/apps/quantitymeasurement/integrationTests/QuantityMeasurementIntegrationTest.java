package com.apps.quantitymeasurement.integrationTests;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImplementation;

import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuantityMeasurementIntegrationTest {

    private static IQuantityMeasurementRepository repository;
    private static IQuantityMeasurementService service;
    private static QuantityMeasurementController controller;

    @BeforeAll
    static void setUpStack() {
        repository= QuantityMeasurementDatabaseRepository.getInstance();
        service= new QuantityMeasurementServiceImplementation(repository);
        controller= new QuantityMeasurementController(service);
    }

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAllMeasurements();
    }

    @Test
    @Order(1)
    void integration_compareLength_1Foot_equals_12Inches() {
        boolean result= controller.performComparison(
            new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
        );
        assertTrue(result);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @Order(2)
    void integration_compareLength_1Foot_notEquals_1Inch() {
        boolean result= controller.performComparison(
            new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
            new QuantityDTO(1.0, QuantityDTO.LengthUnit.INCHES)
        );
        assertFalse(result);
    }

    @Test
    @Order(3)
    void integration_convertLength_1Foot_to_12Inches_persistedToDb() {
        QuantityDTO result= controller.performConversion(
            new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
            QuantityDTO.LengthUnit.INCHES
        );
        assertEquals(12.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.LengthUnit.INCHES, result.getUnit());

        List<QuantityMeasurementEntity> records= repository.getMeasurementsByOperationType("CONVERT");
        assertEquals(1, records.size());
        assertEquals("FEET", records.get(0).getOperand1Unit());
    }

    @Test
    @Order(4)
    void integration_addLength_1Foot_plus_12Inches_equals_2Feet() {
        QuantityDTO result= controller.performAddition(
            new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
        );
        assertEquals(2.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.LengthUnit.FEET, result.getUnit());
    }

    @Test
    @Order(5)
    void integration_addLength_withTargetUnit_inches() {
        QuantityDTO result= controller.performAddition(
            new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES),
            QuantityDTO.LengthUnit.INCHES
        );
        assertEquals(24.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.LengthUnit.INCHES, result.getUnit());
    }

    @Test
    @Order(6)
    void integration_compareLength_1Yard_equals_36Inches() {
        boolean result= controller.performComparison(
            new QuantityDTO(1.0,  QuantityDTO.LengthUnit.YARDS),
            new QuantityDTO(36.0, QuantityDTO.LengthUnit.INCHES)
        );
        assertTrue(result);
    }

    @Test
    @Order(7)
    void integration_compareWeight_1Kg_equals_1000Grams() {
        boolean result= controller.performComparison(
            new QuantityDTO(1.0,    QuantityDTO.WeightUnit.KILOGRAM),
            new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM)
        );
        assertTrue(result);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @Order(8)
    void integration_convertWeight_1Kg_to_1000Grams() {
        QuantityDTO result= controller.performConversion(
            new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
            QuantityDTO.WeightUnit.GRAM
        );
        assertEquals(1000.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.WeightUnit.GRAM, result.getUnit());
    }

    @Test
    @Order(9)
    void integration_subtractWeight_2Kg_minus_500g_equals_1500g() {
        QuantityDTO result = controller.performSubtraction(
            new QuantityDTO(2.0,   QuantityDTO.WeightUnit.KILOGRAM),
            new QuantityDTO(500.0, QuantityDTO.WeightUnit.GRAM),
            QuantityDTO.WeightUnit.GRAM
        );
        assertEquals(1500.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.WeightUnit.GRAM, result.getUnit());
    }

    @Test
    @Order(10)
    void integration_divideWeight_10Kg_by_5Kg_equals_2() {
        double result = controller.performDivision(
            new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM),
            new QuantityDTO(5.0,  QuantityDTO.WeightUnit.KILOGRAM)
        );
        assertEquals(2.0, result, 0.001);
    }

    // ── VOLUME ────────────────────────────────────────────────────────────────

    @Test
    @Order(11)
    void integration_compareVolume_1Litre_equals_1000ml() {
        boolean result = controller.performComparison(
            new QuantityDTO(1.0,    QuantityDTO.VolumeUnit.LITRE),
            new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE)
        );
        assertTrue(result);
    }

    @Test
    @Order(12)
    void integration_convertVolume_1Gallon_to_Millilitres() {
        QuantityDTO result = controller.performConversion(
            new QuantityDTO(1.0, QuantityDTO.VolumeUnit.GALLON),
            QuantityDTO.VolumeUnit.MILLILITRE
        );
        assertEquals(3785.41, result.getValue(), 1.0);
        assertEquals(QuantityDTO.VolumeUnit.MILLILITRE, result.getUnit());
    }

    @Test
    @Order(13)
    void integration_addVolume_1Litre_plus_1000ml_equals_2Litres() {
        QuantityDTO result = controller.performAddition(
            new QuantityDTO(1.0,    QuantityDTO.VolumeUnit.LITRE),
            new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE)
        );
        assertEquals(2.0, result.getValue(), 0.01);
    }

    // ── TEMPERATURE ───────────────────────────────────────────────────────────

    @Test
    @Order(14)
    void integration_compareTemperature_100C_equals_212F() {
        boolean result = controller.performComparison(
            new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
            new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT)
        );
        assertTrue(result);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @Order(15)
    void integration_convertTemperature_100C_to_212F() {
        QuantityDTO result = controller.performConversion(
            new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
            QuantityDTO.TemperatureUnit.FAHRENHEIT
        );
        assertEquals(212.0, result.getValue(), 0.01);
        assertEquals(QuantityDTO.TemperatureUnit.FAHRENHEIT, result.getUnit());
    }

    @Test
    @Order(16)
    void integration_convertTemperature_0C_to_32F() {
        QuantityDTO result = controller.performConversion(
            new QuantityDTO(0.0, QuantityDTO.TemperatureUnit.CELSIUS),
            QuantityDTO.TemperatureUnit.FAHRENHEIT
        );
        assertEquals(32.0, result.getValue(), 0.01);
    }

    @Test
    @Order(17)
    void integration_addTemperature_throwsQuantityMeasurementException() {
        assertThrows(QuantityMeasurementException.class, () ->
            controller.performAddition(
                new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
                new QuantityDTO(50.0,  QuantityDTO.TemperatureUnit.CELSIUS)
            )
        );
    }

    // ── Cross-type guard ─────────────────────────────────────────────────────

    @Test
    @Order(18)
    void integration_compareDifferentTypes_throwsQuantityMeasurementException() {
        assertThrows(QuantityMeasurementException.class, () ->
            controller.performComparison(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM)
            )
        );
    }

    @Test
    @Order(19)
    void integration_addDifferentTypes_throwsQuantityMeasurementException() {
        assertThrows(QuantityMeasurementException.class, () ->
            controller.performAddition(
                new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE)
            )
        );
    }

    @Test
    @Order(20)
    void integration_divideDifferentTypes_throwsQuantityMeasurementException() {
        assertThrows(QuantityMeasurementException.class, () ->
            controller.performDivision(
                new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET),
                new QuantityDTO(5.0,  QuantityDTO.WeightUnit.KILOGRAM)
            )
        );
    }

    @Test
    @Order(21)
    void integration_multipleOperations_allPersistedToDatabase() {
        controller.performComparison(
            new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
        );
        controller.performConversion(
            new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
            QuantityDTO.TemperatureUnit.FAHRENHEIT
        );
        controller.performAddition(
            new QuantityDTO(1.0,    QuantityDTO.WeightUnit.KILOGRAM),
            new QuantityDTO(500.0,  QuantityDTO.WeightUnit.GRAM)
        );
        controller.performDivision(
            new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM),
            new QuantityDTO(5.0,  QuantityDTO.WeightUnit.KILOGRAM)
        );

        assertEquals(4, repository.getTotalCount());
    }

    @Test
    @Order(22)
    void integration_getMeasurementsByOperationType_returnsCorrectSubset() {
        controller.performComparison(
            new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
        );
        controller.performComparison(
            new QuantityDTO(1.0,    QuantityDTO.WeightUnit.KILOGRAM),
            new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM)
        );
        controller.performConversion(
            new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
            QuantityDTO.LengthUnit.INCHES
        );

        List<QuantityMeasurementEntity> comparisons = repository.getMeasurementsByOperationType("COMPARE");
        List<QuantityMeasurementEntity> conversions = repository.getMeasurementsByOperationType("CONVERT");

        assertEquals(2, comparisons.size());
        assertEquals(1, conversions.size());
        assertEquals(3, repository.getTotalCount());
    }

    @Test
    @Order(23)
    void integration_deleteAllMeasurements_clearsDatabase() {
        controller.performComparison(
            new QuantityDTO(1.0,  QuantityDTO.LengthUnit.FEET),
            new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
        );
        assertEquals(1, repository.getTotalCount());

        repository.deleteAllMeasurements();

        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    @Test
    @Order(24)
    void integration_poolStatistics_returnsMeaningfulString() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertFalse(stats.isBlank());
    }
}