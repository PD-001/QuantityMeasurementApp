CREATE TABLE IF NOT EXISTS quantity_measurements (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    operation_type VARCHAR(50) NOT NULL,
    operand1_value VARCHAR(50) NOT NULL,
    operand1_unit VARCHAR(50) NOT NULL,
    operand2_value VARCHAR(50),
    operand2_unit VARCHAR(50),
    result_value VARCHAR(50),
    result_unit VARCHAR(50),
    success BOOLEAN NOT NULL DEFAULT TRUE,
    error_message VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS quantity_measurement_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    measurement_id BIGINT NOT NULL,
    operation_type VARCHAR(50) NOT NULL,
    operand1_value VARCHAR(50) NOT NULL,
    operand1_unit VARCHAR(50) NOT NULL,
    operand2_value VARCHAR(50),
    operand2_unit VARCHAR(50),
    result_value VARCHAR(50),
    result_unit VARCHAR(50),
    success BOOLEAN NOT NULL DEFAULT TRUE,
    error_message VARCHAR(500),
    archived_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (measurement_id) REFERENCES quantity_measurements(id)
);