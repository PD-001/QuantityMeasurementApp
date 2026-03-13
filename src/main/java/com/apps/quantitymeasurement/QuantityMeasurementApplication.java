package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImplementation;
import com.apps.quantitymeasurement.util.DatabaseConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class QuantityMeasurementApplication {

	private static final Logger logger= Logger.getLogger(QuantityMeasurementApplication.class.getName());
	private static QuantityMeasurementApplication instance;

	private final IQuantityMeasurementRepository repository;
	private final QuantityMeasurementController controller;

	private QuantityMeasurementApplication() {

		DatabaseConfig config= DatabaseConfig.getInstance();
		String repoType= config.getRepositoryType();

		if ("database".equalsIgnoreCase(repoType)) {
			logger.info("Initializing with DatabaseRepository");
			this.repository= QuantityMeasurementDatabaseRepository.getInstance();
		} else {
			logger.info("Initializing with CacheRepository");
			this.repository= QuantityMeasurementCacheRepository.getInstance();
		}

		IQuantityMeasurementService service= new QuantityMeasurementServiceImplementation(repository);
		this.controller= new QuantityMeasurementController(service);

		logger.info("QuantityMeasurementApplication initialized — repository: "+ repoType);
	}

	public static synchronized QuantityMeasurementApplication getInstance() {

		if (instance==null)
			instance= new QuantityMeasurementApplication();

		return instance;
	}

	public QuantityMeasurementController getController() {
		return controller;
	}

	public void deleteAllMeasurements() {
		repository.deleteAllMeasurements();
		logger.info("All measurements deleted");
	}

	public void closeResources() {
		repository.releaseResources();
		logger.info("Resources released — "+ repository.getPoolStatistics());
	}

	public static void main(String[] args) {

		QuantityMeasurementApplication app= QuantityMeasurementApplication.getInstance();
		QuantityMeasurementController ctrl= app.getController();

		ctrl.performComparison(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
		);

		ctrl.performConversion(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			QuantityDTO.LengthUnit.INCHES
		);

		ctrl.performAddition(
			new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
			new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES)
		);

		ctrl.performComparison(
			new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
			new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM)
		);

		ctrl.performConversion(
			new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
			QuantityDTO.WeightUnit.GRAM
		);

		ctrl.performSubtraction(
			new QuantityDTO(2.0, QuantityDTO.WeightUnit.KILOGRAM),
			new QuantityDTO(500.0, QuantityDTO.WeightUnit.GRAM)
		);

		ctrl.performDivision(
			new QuantityDTO(10.0, QuantityDTO.WeightUnit.KILOGRAM),
			new QuantityDTO(5.0, QuantityDTO.WeightUnit.KILOGRAM)
		);

		ctrl.performComparison(
			new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE),
			new QuantityDTO(1000.0, QuantityDTO.VolumeUnit.MILLILITRE)
		);

		ctrl.performConversion(
			new QuantityDTO(1.0, QuantityDTO.VolumeUnit.GALLON),
			QuantityDTO.VolumeUnit.MILLILITRE
		);

		ctrl.performComparison(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			new QuantityDTO(212.0, QuantityDTO.TemperatureUnit.FAHRENHEIT)
		);

		ctrl.performConversion(
			new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
			QuantityDTO.TemperatureUnit.FAHRENHEIT
		);

		try {
			ctrl.performAddition(
				new QuantityDTO(100.0, QuantityDTO.TemperatureUnit.CELSIUS),
				new QuantityDTO(50.0, QuantityDTO.TemperatureUnit.CELSIUS)
			);
		} catch (QuantityMeasurementException e) {
			logger.warning("Expected error for temperature addition: "+ e.getMessage());
		}

		List<QuantityMeasurementEntity> all= app.repository.getAllMeasurements();
		logger.info("Total measurements stored: "+ all.size());
		all.forEach(e -> logger.info("  "+ e.toString()));

		logger.info("Pool statistics: "+ app.repository.getPoolStatistics());

		try {
			org.h2.tools.Server h2Server= org.h2.tools.Server
				.createWebServer("-web", "-webAllowOthers", "-webPort", "8082")
				.start();

			logger.info("- - -");
			logger.info("H2 Console running at: http://localhost:8082");
			logger.info("JDBC URL: jdbc:h2:file:./data/quantitydb");
			logger.info("Username: sa");
			logger.info("Password:");
			logger.info(">>> Press ENTER in this console to stop the H2 server <<<");
			logger.info("- - -");

			System.in.read();

			h2Server.stop();
			logger.info("H2 Console stopped");

		} catch (SQLException | java.io.IOException e) {
			logger.severe("H2 Console error: "+ e.getMessage());
			e.printStackTrace();
		}

		app.deleteAllMeasurements();
		app.closeResources();
	}
}