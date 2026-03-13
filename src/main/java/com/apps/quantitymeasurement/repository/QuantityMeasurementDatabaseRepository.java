package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.exception.DatabaseException;
import com.apps.quantitymeasurement.util.ConnectionPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {

	private static final Logger logger= Logger.getLogger(QuantityMeasurementDatabaseRepository.class.getName());
	private static QuantityMeasurementDatabaseRepository instance;
	private final ConnectionPool connectionPool;

	private static final String INSERT_SQL=
		"INSERT INTO quantity_measurements " +
		"(operation_type, operand1_value, operand1_unit, operand2_value, operand2_unit, " +
		"result_value, result_unit, success, error_message) " +
		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String SELECT_ALL_SQL=
		"SELECT * FROM quantity_measurements ORDER BY created_at DESC";

	private static final String SELECT_BY_OPERATION_SQL=
		"SELECT * FROM quantity_measurements WHERE UPPER(operation_type) = UPPER(?) ORDER BY created_at DESC";

	private static final String COUNT_SQL=
		"SELECT COUNT(*) FROM quantity_measurements";

	private static final String DELETE_ALL_SQL=
		"DELETE FROM quantity_measurements";

	private QuantityMeasurementDatabaseRepository(ConnectionPool connectionPool) {
		this.connectionPool= connectionPool;
		initializeSchema();
		logger.info("QuantityMeasurementDatabaseRepository initialized");
	}

	public static synchronized QuantityMeasurementDatabaseRepository getInstance() {

		if (instance==null)
			instance= new QuantityMeasurementDatabaseRepository(ConnectionPool.getInstance());

		return instance;
	}

	private void initializeSchema() {

		Connection connection= connectionPool.getConnection();

		try (InputStream input= getClass().getClassLoader().getResourceAsStream("db/schema.sql")) {

			if (input==null) {
				logger.warning("schema.sql not found — skipping schema initialization");
				return;
			}

			String sql= new BufferedReader(new InputStreamReader(input))
				.lines().collect(Collectors.joining("\n"));

			for (String statement : sql.split(";")) {
				String trimmed= statement.trim();
				if (!trimmed.isEmpty() && !trimmed.startsWith("--")) {
					try (Statement stmt= connection.createStatement()) {
						stmt.execute(trimmed);
					}
				}
			}

			logger.info("Database schema initialized successfully");

		} catch (IOException | SQLException e) {
			throw new DatabaseException("Failed to initialize schema: "+ e.getMessage(), e);
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}

	@Override
	public void save(QuantityMeasurementEntity entity) {

		Connection connection= connectionPool.getConnection();

		try (PreparedStatement stmt= connection.prepareStatement(INSERT_SQL)) {

			stmt.setString(1, entity.getOperationType());
			stmt.setString(2, entity.getOperand1Value());
			stmt.setString(3, entity.getOperand1Unit());
			stmt.setString(4, entity.getOperand2Value());
			stmt.setString(5, entity.getOperand2Unit());
			stmt.setString(6, entity.getResultValue());
			stmt.setString(7, entity.getResultUnit());
			stmt.setBoolean(8, entity.isSuccess());
			stmt.setString(9, entity.getErrorMessage());

			stmt.executeUpdate();
			logger.fine("Entity saved to database: "+ entity.getOperationType());

		} catch (SQLException e) {
			throw new DatabaseException("Failed to save entity: "+ e.getMessage(), e);
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}

	@Override
	public List<QuantityMeasurementEntity> getAllMeasurements() {

		Connection connection= connectionPool.getConnection();
		List<QuantityMeasurementEntity> results= new ArrayList<>();

		try (PreparedStatement stmt= connection.prepareStatement(SELECT_ALL_SQL);
			 ResultSet rs= stmt.executeQuery()) {

			while (rs.next()) {
				results.add(mapRow(rs));
			}

		} catch (SQLException e) {
			throw new DatabaseException("Failed to retrieve measurements: "+ e.getMessage(), e);
		} finally {
			connectionPool.releaseConnection(connection);
		}

		return results;
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByOperationType(String operationType) {

		Connection connection= connectionPool.getConnection();
		List<QuantityMeasurementEntity> results= new ArrayList<>();

		try (PreparedStatement stmt= connection.prepareStatement(SELECT_BY_OPERATION_SQL)) {

			stmt.setString(1, operationType);

			try (ResultSet rs= stmt.executeQuery()) {
				while (rs.next()) {
					results.add(mapRow(rs));
				}
			}

		} catch (SQLException e) {
			throw new DatabaseException("Failed to retrieve by operation type: "+ e.getMessage(), e);
		} finally {
			connectionPool.releaseConnection(connection);
		}

		return results;
	}

	@Override
	public List<QuantityMeasurementEntity> getMeasurementsByMeasurementType(String measurementType) {

		return getAllMeasurements().stream()
			.filter(e -> measurementType.equalsIgnoreCase(e.getOperand1Unit()))
			.collect(Collectors.toList());
	}

	@Override
	public int getTotalCount() {

		Connection connection= connectionPool.getConnection();

		try (PreparedStatement stmt= connection.prepareStatement(COUNT_SQL);
			 ResultSet rs= stmt.executeQuery()) {

			if (rs.next()) return rs.getInt(1);

		} catch (SQLException e) {
			throw new DatabaseException("Failed to get count: "+ e.getMessage(), e);
		} finally {
			connectionPool.releaseConnection(connection);
		}

		return 0;
	}

	@Override
	public void deleteAllMeasurements() {

		Connection connection= connectionPool.getConnection();

		try (PreparedStatement stmt= connection.prepareStatement(DELETE_ALL_SQL)) {
			stmt.executeUpdate();
			logger.info("All measurements deleted from database");
		} catch (SQLException e) {
			throw new DatabaseException("Failed to delete measurements: "+ e.getMessage(), e);
		} finally {
			connectionPool.releaseConnection(connection);
		}
	}

	@Override
	public String getPoolStatistics() {
		return connectionPool.getPoolStatistics();
	}

	@Override
	public void releaseResources() {
		connectionPool.closeAll();
		logger.info("Database resources released");
	}

	private QuantityMeasurementEntity mapRow(ResultSet rs) throws SQLException {

		String operationType= rs.getString("operation_type");
		String op1Value= rs.getString("operand1_value");
		String op1Unit= rs.getString("operand1_unit");
		String op2Value= rs.getString("operand2_value");
		String op2Unit= rs.getString("operand2_unit");
		String resultValue= rs.getString("result_value");
		String resultUnit= rs.getString("result_unit");
		boolean success= rs.getBoolean("success");
		String errorMessage= rs.getString("error_message");

		if (!success) {
			return new QuantityMeasurementEntity(operationType, op1Value, op1Unit, errorMessage);
		} else if (op2Value!=null) {
			return new QuantityMeasurementEntity(operationType, op1Value, op1Unit, op2Value, op2Unit, resultValue, resultUnit);
		} else {
			return new QuantityMeasurementEntity(operationType, op1Value, op1Unit, resultValue, resultUnit);
		}
	}
}