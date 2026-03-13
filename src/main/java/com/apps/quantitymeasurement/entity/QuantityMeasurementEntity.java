package com.apps.quantitymeasurement.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

	private static final long serialVersionUID= 1L;

	private String operationType;
	private String operand1Value;
	private String operand1Unit;
	private String operand2Value;
	private String operand2Unit;
	private String resultValue;
	private String resultUnit;
	private boolean success;
	private String errorMessage;

	public QuantityMeasurementEntity(String operationType, String operand1Value, String operand1Unit, String resultValue, String resultUnit) {
		this.operationType= operationType;
		this.operand1Value= operand1Value;
		this.operand1Unit= operand1Unit;
		this.resultValue= resultValue;
		this.resultUnit= resultUnit;
		this.success= true;
	}

	public QuantityMeasurementEntity(String operationType, String operand1Value, String operand1Unit, String operand2Value, String operand2Unit, String resultValue, String resultUnit) {
		this.operationType= operationType;
		this.operand1Value= operand1Value;
		this.operand1Unit= operand1Unit;
		this.operand2Value= operand2Value;
		this.operand2Unit= operand2Unit;
		this.resultValue= resultValue;
		this.resultUnit= resultUnit;
		this.success= true;
	}

	public QuantityMeasurementEntity(String operationType, String operand1Value, String operand1Unit, String errorMessage) {
		this.operationType= operationType;
		this.operand1Value= operand1Value;
		this.operand1Unit= operand1Unit;
		this.success= false;
		this.errorMessage= errorMessage;
	}

	public String getOperationType() { return operationType; }
	public String getOperand1Value() { return operand1Value; }
	public String getOperand1Unit() { return operand1Unit; }
	public String getOperand2Value() { return operand2Value; }
	public String getOperand2Unit() { return operand2Unit; }
	public String getResultValue() { return resultValue; }
	public String getResultUnit() { return resultUnit; }
	public boolean isSuccess() { return success; }
	public String getErrorMessage() { return errorMessage; }

	@Override
	public String toString() {
		if (!success) return "["+ operationType +"] ERROR: "+ errorMessage;

		if (operand2Value!=null) return "["+ operationType +"] "+ operand1Value +":"+ operand1Unit +" op "+ operand2Value +":"+ operand2Unit+" = "+ resultValue +":"+ resultUnit;

		return "["+ operationType +"] "+ operand1Value +":"+ operand1Unit +" = "+ resultValue +":"+ resultUnit;
	}
}