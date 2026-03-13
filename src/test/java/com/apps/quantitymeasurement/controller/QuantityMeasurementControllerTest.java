package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuantityMeasurementControllerTest {

	private IQuantityMeasurementService service;

	private QuantityMeasurementController controller;

	@BeforeEach
	void setUp() {
		controller= new QuantityMeasurementController(service);
	}

	@Test
	void performComparison_delegatesToService_returnsTrue() {
		QuantityDTO q1= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO q2= new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
		when(service.compare(q1, q2)).thenReturn(true);

		boolean result= controller.performComparison(q1, q2);

		assertTrue(result);
		verify(service, times(1)).compare(q1, q2);
	}

	@Test
	void performComparison_delegatesToService_returnsFalse() {
		QuantityDTO q1= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO q2= new QuantityDTO(5.0, QuantityDTO.LengthUnit.INCHES);
		when(service.compare(q1, q2)).thenReturn(false);

		boolean result= controller.performComparison(q1, q2);

		assertFalse(result);
		verify(service, times(1)).compare(q1, q2);
	}

	@Test
	void performComparison_serviceThrows_exceptionPropagates() {
		QuantityDTO q1= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO q2= new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
		when(service.compare(q1, q2)).thenThrow(new RuntimeException("Type mismatch"));

		assertThrows(RuntimeException.class, () -> controller.performComparison(q1, q2));
	}

	@Test
	void performConversion_delegatesToService_returnsConvertedDTO() {
		QuantityDTO input= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO expected= new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
		when(service.convert(input, QuantityDTO.LengthUnit.INCHES)).thenReturn(expected);

		QuantityDTO result= controller.performConversion(input, QuantityDTO.LengthUnit.INCHES);

		assertEquals(expected, result);
		verify(service, times(1)).convert(input, QuantityDTO.LengthUnit.INCHES);
	}

	@Test
	void performConversion_celsiusToFahrenheit_returnsCorrectDTO() {
		QuantityDTO input= new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
		QuantityDTO expected= new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT);
		when(service.convert(input, QuantityDTO.TemperatureUnit.FAHRENHEIT)).thenReturn(expected);

		QuantityDTO result= controller.performConversion(input, QuantityDTO.TemperatureUnit.FAHRENHEIT);

		assertNotNull(result);
		assertEquals(212.0, result.getValue(), 0.01);
		verify(service, times(1)).convert(input, QuantityDTO.TemperatureUnit.FAHRENHEIT);
	}

	@Test
	void performConversion_serviceThrows_exceptionPropagates() {
		QuantityDTO input= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		when(service.convert(any(), any())).thenThrow(new RuntimeException("Conversion failed"));

		assertThrows(RuntimeException.class,
			() -> controller.performConversion(input, QuantityDTO.WeightUnit.KILOGRAM));
	}

	@Test
	void performAddition_delegatesToService_returnsSum() {
		QuantityDTO q1= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO q2= new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
		QuantityDTO expected= new QuantityDTO(2.0, QuantityDTO.LengthUnit.FEET);
		when(service.add(q1, q2)).thenReturn(expected);

		QuantityDTO result= controller.performAddition(q1, q2);

		assertEquals(expected, result);
		verify(service, times(1)).add(q1, q2);
	}

	@Test
	void performAddition_serviceThrows_exceptionPropagates() {
		QuantityDTO q1= new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS);
		QuantityDTO q2= new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS);
		when(service.add(q1, q2)).thenThrow(new RuntimeException("Temperature addition not supported"));

		assertThrows(RuntimeException.class, () -> controller.performAddition(q1, q2));
	}

	@Test
	void performAddition_withTargetUnit_delegatesToService() {
		QuantityDTO q1= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO q2= new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
		QuantityDTO expected= new QuantityDTO(24.0, QuantityDTO.LengthUnit.INCHES);
		when(service.add(q1, q2, QuantityDTO.LengthUnit.INCHES)).thenReturn(expected);

		QuantityDTO result= controller.performAddition(q1, q2, QuantityDTO.LengthUnit.INCHES);

		assertEquals(expected, result);
		verify(service, times(1)).add(q1, q2, QuantityDTO.LengthUnit.INCHES);
	}

	@Test
	void performSubtraction_delegatesToService_returnsDifference() {
		QuantityDTO q1= new QuantityDTO(2.0, QuantityDTO.WeightUnit.KILOGRAM);
		QuantityDTO q2= new QuantityDTO(500.0, QuantityDTO.WeightUnit.GRAM);
		QuantityDTO expected= new QuantityDTO(1.5, QuantityDTO.WeightUnit.KILOGRAM);
		when(service.subtract(q1, q2)).thenReturn(expected);

		QuantityDTO result= controller.performSubtraction(q1, q2);

		assertEquals(expected, result);
		verify(service, times(1)).subtract(q1, q2);
	}

	@Test
	void performSubtraction_serviceThrows_exceptionPropagates() {
		QuantityDTO q1= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO q2= new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM);
		when(service.subtract(q1, q2)).thenThrow(new RuntimeException("Type mismatch"));

		assertThrows(RuntimeException.class, () -> controller.performSubtraction(q1, q2));
	}

	@Test
	void performSubtraction_withTargetUnit_delegatesToService() {
		QuantityDTO q1= new QuantityDTO(2.0, QuantityDTO.WeightUnit.KILOGRAM);
		QuantityDTO q2= new QuantityDTO(500.0, QuantityDTO.WeightUnit.GRAM);
		QuantityDTO expected= new QuantityDTO(1500.0, QuantityDTO.WeightUnit.GRAM);
		when(service.subtract(q1, q2, QuantityDTO.WeightUnit.GRAM)).thenReturn(expected);

		QuantityDTO result= controller.performSubtraction(q1, q2, QuantityDTO.WeightUnit.GRAM);

		assertEquals(expected, result);
		verify(service, times(1)).subtract(q1, q2, QuantityDTO.WeightUnit.GRAM);
	}

	@Test
	void performDivision_delegatesToService_returnsScalar() {
		QuantityDTO q1= new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM);
		QuantityDTO q2= new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM);
		when(service.divide(q1, q2)).thenReturn(2.0);

		double result= controller.performDivision(q1, q2);

		assertEquals(2.0, result, 0.001);
		verify(service, times(1)).divide(q1, q2);
	}

	@Test
	void performDivision_divideByZero_serviceThrows_exceptionPropagates() {
		QuantityDTO q1= new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM);
		QuantityDTO q2= new QuantityDTO(0.0, QuantityDTO.WeightUnit.KILOGRAM);
		when(service.divide(q1, q2)).thenThrow(new ArithmeticException("Division by zero"));

		assertThrows(ArithmeticException.class, () -> controller.performDivision(q1, q2));
	}

	@Test
	void performDivision_differentTypes_serviceThrows_exceptionPropagates() {
		QuantityDTO q1= new QuantityDTO(10.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO q2= new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM);
		when(service.divide(q1, q2)).thenThrow(new RuntimeException("Type mismatch"));

		assertThrows(RuntimeException.class, () -> controller.performDivision(q1, q2));
	}

	@Test
	void performComparison_noOtherServiceMethodsCalled() {
		QuantityDTO q1= new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET);
		QuantityDTO q2= new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES);
		when(service.compare(q1, q2)).thenReturn(true);

		controller.performComparison(q1, q2);

		verify(service, times(1)).compare(q1, q2);
		verifyNoMoreInteractions(service);
	}

}