package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.DatabaseException;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class QuantityMeasurementDatabaseRepositoryTest {

    private static QuantityMeasurementDatabaseRepository repository;

    @BeforeAll
    static void initRepository() {
        // Uses H2 in-memory DB via application.properties / DatabaseConfig defaults
        repository = QuantityMeasurementDatabaseRepository.getInstance();
    }

    @BeforeEach
    void clearDatabase() {
        repository.deleteAllMeasurements();
    }

    // ── save ─────────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    void save_successEntityWithTwoOperands_persistsToDatabase() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
            "ADD", "1.0", "FEET", "12.0", "INCHES", "24.0", "INCHES"
        );
        repository.save(entity);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @Order(2)
    void save_successEntityWithSingleOperand_persistsToDatabase() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
            "CONVERT", "1.0", "FEET", "12.0", "INCHES"
        );
        repository.save(entity);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @Order(3)
    void save_errorEntity_persistsToDatabase() {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity(
            "COMPARE", "1.0", "FEET", "Type mismatch error"
        );
        repository.save(entity);
        assertEquals(1, repository.getTotalCount());
    }

    @Test
    @Order(4)
    void save_multipleEntities_allPersisted() {
        repository.save(new QuantityMeasurementEntity("ADD",     "1.0", "FEET",     "12.0", "INCHES", "24.0", "INCHES"));
        repository.save(new QuantityMeasurementEntity("CONVERT", "1.0", "KILOGRAM", "1000.0", "GRAM"));
        repository.save(new QuantityMeasurementEntity("COMPARE", "1.0", "LITRE",    "1000.0", "MILLILITRE", "true", ""));
        assertEquals(3, repository.getTotalCount());
    }

    // ── getAllMeasurements ─────────────────────────────────────────────────────

    @Test
    @Order(5)
    void getAllMeasurements_emptyDatabase_returnsEmptyList() {
        List<QuantityMeasurementEntity> result = repository.getAllMeasurements();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(6)
    void getAllMeasurements_afterSave_returnsAllEntities() {
        repository.save(new QuantityMeasurementEntity("ADD", "1.0", "FEET", "12.0", "INCHES", "24.0", "INCHES"));
        repository.save(new QuantityMeasurementEntity("CONVERT", "100.0", "CELSIUS", "212.0", "FAHRENHEIT"));

        List<QuantityMeasurementEntity> result = repository.getAllMeasurements();
        assertEquals(2, result.size());
    }

    @Test
    @Order(7)
    void getAllMeasurements_returnsDefensiveCopy_originalNotAffected() {
        repository.save(new QuantityMeasurementEntity("ADD", "1.0", "FEET", "12.0", "INCHES", "24.0", "INCHES"));

        List<QuantityMeasurementEntity> result = repository.getAllMeasurements();
        result.clear();

        assertEquals(1, repository.getTotalCount());
    }

    // ── getMeasurementsByOperationType ────────────────────────────────────────

    @Test
    @Order(8)
    void getMeasurementsByOperationType_matchingEntries_returnsFiltered() {
        repository.save(new QuantityMeasurementEntity("ADD",     "1.0", "FEET", "12.0", "INCHES", "24.0", "INCHES"));
        repository.save(new QuantityMeasurementEntity("ADD",     "2.0", "FEET", "6.0",  "INCHES", "30.0", "INCHES"));
        repository.save(new QuantityMeasurementEntity("CONVERT", "1.0", "FEET", "12.0", "INCHES"));

        List<QuantityMeasurementEntity> result = repository.getMeasurementsByOperationType("ADD");
        assertEquals(2, result.size());
        result.forEach(e -> assertEquals("ADD", e.getOperationType()));
    }

    @Test
    @Order(9)
    void getMeasurementsByOperationType_caseInsensitive_returnsResults() {
        repository.save(new QuantityMeasurementEntity("CONVERT", "1.0", "FEET", "12.0", "INCHES"));

        List<QuantityMeasurementEntity> lower = repository.getMeasurementsByOperationType("convert");
        List<QuantityMeasurementEntity> upper = repository.getMeasurementsByOperationType("CONVERT");

        assertEquals(1, lower.size());
        assertEquals(1, upper.size());
    }

    @Test
    @Order(10)
    void getMeasurementsByOperationType_noMatch_returnsEmptyList() {
        repository.save(new QuantityMeasurementEntity("ADD", "1.0", "FEET", "12.0", "INCHES", "24.0", "INCHES"));

        List<QuantityMeasurementEntity> result = repository.getMeasurementsByOperationType("DIVIDE");
        assertTrue(result.isEmpty());
    }

    // ── getMeasurementsByMeasurementType ──────────────────────────────────────

    @Test
    @Order(11)
    void getMeasurementsByMeasurementType_matchingUnit_returnsFiltered() {
        repository.save(new QuantityMeasurementEntity("ADD",     "1.0", "FEET",     "2.0", "FEET",     "3.0", "FEET"));
        repository.save(new QuantityMeasurementEntity("CONVERT", "1.0", "KILOGRAM", "1000.0", "GRAM"));

        List<QuantityMeasurementEntity> result = repository.getMeasurementsByMeasurementType("FEET");
        assertEquals(1, result.size());
        assertEquals("FEET", result.get(0).getOperand1Unit());
    }

    @Test
    @Order(12)
    void getMeasurementsByMeasurementType_caseInsensitive_returnsResults() {
        repository.save(new QuantityMeasurementEntity("ADD", "1.0", "FEET", "2.0", "FEET", "3.0", "FEET"));

        List<QuantityMeasurementEntity> result = repository.getMeasurementsByMeasurementType("feet");
        assertEquals(1, result.size());
    }

    @Test
    @Order(13)
    void getMeasurementsByMeasurementType_noMatch_returnsEmptyList() {
        repository.save(new QuantityMeasurementEntity("ADD", "1.0", "FEET", "2.0", "FEET", "3.0", "FEET"));

        List<QuantityMeasurementEntity> result = repository.getMeasurementsByMeasurementType("CELSIUS");
        assertTrue(result.isEmpty());
    }

    // ── getTotalCount ─────────────────────────────────────────────────────────

    @Test
    @Order(14)
    void getTotalCount_emptyDatabase_returnsZero() {
        assertEquals(0, repository.getTotalCount());
    }

    @Test
    @Order(15)
    void getTotalCount_afterSavingThree_returnsThree() {
        repository.save(new QuantityMeasurementEntity("ADD",     "1.0", "FEET",     "2.0", "FEET",     "3.0", "FEET"));
        repository.save(new QuantityMeasurementEntity("CONVERT", "1.0", "KILOGRAM", "1000.0", "GRAM"));
        repository.save(new QuantityMeasurementEntity("COMPARE", "1.0", "LITRE",    "1000.0", "MILLILITRE", "true", ""));
        assertEquals(3, repository.getTotalCount());
    }

    // ── deleteAllMeasurements ─────────────────────────────────────────────────

    @Test
    @Order(16)
    void deleteAllMeasurements_afterSaving_countBecomesZero() {
        repository.save(new QuantityMeasurementEntity("ADD", "1.0", "FEET", "2.0", "FEET", "3.0", "FEET"));
        repository.save(new QuantityMeasurementEntity("CONVERT", "1.0", "KILOGRAM", "1000.0", "GRAM"));

        repository.deleteAllMeasurements();

        assertEquals(0, repository.getTotalCount());
        assertTrue(repository.getAllMeasurements().isEmpty());
    }

    // ── getPoolStatistics ─────────────────────────────────────────────────────

    @Test
    @Order(17)
    void getPoolStatistics_returnsNonNullString() {
        String stats = repository.getPoolStatistics();
        assertNotNull(stats);
        assertFalse(stats.isEmpty());
    }

    @Test
    @Order(18)
    void getPoolStatistics_containsExpectedKeywords() {
        String stats = repository.getPoolStatistics();
        assertTrue(stats.contains("active") || stats.contains("ConnectionPool") || stats.contains("idle"));
    }

    // ── Singleton ─────────────────────────────────────────────────────────────

    @Test
    @Order(19)
    void getInstance_calledTwice_returnsSameInstance() {
        QuantityMeasurementDatabaseRepository first  = QuantityMeasurementDatabaseRepository.getInstance();
        QuantityMeasurementDatabaseRepository second = QuantityMeasurementDatabaseRepository.getInstance();
        assertSame(first, second);
    }

    // ── mapRow integrity ──────────────────────────────────────────────────────

    @Test
    @Order(20)
    void save_andRetrieve_errorEntity_hasCorrectFields() {
        repository.save(new QuantityMeasurementEntity("COMPARE", "1.0", "FEET", "Type mismatch"));

        List<QuantityMeasurementEntity> results = repository.getAllMeasurements();
        assertEquals(1, results.size());

        QuantityMeasurementEntity retrieved = results.get(0);
        assertFalse(retrieved.isSuccess());
        assertEquals("COMPARE",       retrieved.getOperationType());
        assertEquals("1.0",           retrieved.getOperand1Value());
        assertEquals("FEET",          retrieved.getOperand1Unit());
        assertEquals("Type mismatch", retrieved.getErrorMessage());
    }

    @Test
    @Order(21)
    void save_andRetrieve_twoOperandEntity_hasCorrectFields() {
        repository.save(new QuantityMeasurementEntity(
            "ADD", "1.0", "FEET", "12.0", "INCHES", "24.0", "INCHES"
        ));

        List<QuantityMeasurementEntity> results = repository.getAllMeasurements();
        assertEquals(1, results.size());

        QuantityMeasurementEntity retrieved = results.get(0);
        assertTrue(retrieved.isSuccess());
        assertEquals("ADD",    retrieved.getOperationType());
        assertEquals("1.0",    retrieved.getOperand1Value());
        assertEquals("FEET",   retrieved.getOperand1Unit());
        assertEquals("12.0",   retrieved.getOperand2Value());
        assertEquals("INCHES", retrieved.getOperand2Unit());
        assertEquals("24.0",   retrieved.getResultValue());
        assertEquals("INCHES", retrieved.getResultUnit());
    }
}