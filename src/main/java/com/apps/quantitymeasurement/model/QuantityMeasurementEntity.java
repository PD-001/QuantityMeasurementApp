package com.apps.quantitymeasurement.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name= "quantity_measurements")
public class QuantityMeasurementEntity implements Serializable {

    private static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name= "operation_type", nullable= false, length= 50)
    private String operationType;

    @Column(name= "operand1_value", nullable= false, length= 50)
    private String operand1Value;

    @Column(name= "operand1_unit", nullable= false, length= 50)
    private String operand1Unit;

    @Column(name= "operand2_value", length= 50)
    private String operand2Value;

    @Column(name= "operand2_unit", length= 50)
    private String operand2Unit;

    @Column(name= "result_value", length= 50)
    private String resultValue;

    @Column(name= "result_unit", length= 50)
    private String resultUnit;

    @Column(name= "success", nullable= false)
    private boolean success;

    @Column(name= "error_message", length= 500)
    private String errorMessage;

    @Column(name= "created_at", nullable= false, updatable= false)
    private LocalDateTime createdAt;
    
    @Column(name = "user_id")
    private Long userId;

    //Required by JPA
    protected QuantityMeasurementEntity() {}

    //Auto-set createdAt before inserting
    @PrePersist
    protected void onCreate() {
        this.createdAt= LocalDateTime.now();
    }

    public QuantityMeasurementEntity(Long userId, String operationType, String operand1Value, String operand1Unit, String resultValue, String resultUnit) {
        this.userId= userId;
    	this.operationType= operationType;
        this.operand1Value= operand1Value;
        this.operand1Unit= operand1Unit;
        this.resultValue= resultValue;
        this.resultUnit= resultUnit;
        this.success= true;
    }

    public QuantityMeasurementEntity(Long userId, String operationType, String operand1Value, String operand1Unit, String operand2Value, String operand2Unit, String resultValue, String resultUnit) {
        this.userId= userId;
    	this.operationType= operationType;
        this.operand1Value= operand1Value;
        this.operand1Unit= operand1Unit;
        this.operand2Value= operand2Value;
        this.operand2Unit= operand2Unit;
        this.resultValue= resultValue;
        this.resultUnit= resultUnit;
        this.success= true;
    }

    public QuantityMeasurementEntity(Long userId, String operationType, String operand1Value, String operand1Unit, String errorMessage) {
        this.userId= userId;
    	this.operationType= operationType;
        this.operand1Value= operand1Value;
        this.operand1Unit= operand1Unit;
        this.success= false;
        this.errorMessage= errorMessage;
    }

    // Getters
    
    public Long getUserId(){ return userId; }
    public Long getId(){ return id; }
    public String getOperationType(){ return operationType; }
    public String getOperand1Value(){ return operand1Value; }
    public String getOperand1Unit(){ return operand1Unit; }
    public String getOperand2Value(){ return operand2Value; }
    public String getOperand2Unit(){ return operand2Unit; }
    public String getResultValue(){ return resultValue; }
    public String getResultUnit(){ return resultUnit; }
    public boolean isSuccess(){ return success; }
    public String getErrorMessage(){ return errorMessage; }
    public LocalDateTime getCreatedAt(){ return createdAt; }

    @Override
    public String toString() {
        if (!success)
            return "[" + operationType + "] ERROR: " + errorMessage;
        if (operand2Value != null)
            return "[" + operationType + "] " + operand1Value + ":" + operand1Unit
                   + " op " + operand2Value + ":" + operand2Unit
                   + " = " + resultValue + ":" + resultUnit;
        return "[" + operationType + "] " + operand1Value + ":" + operand1Unit
               + " = " + resultValue + ":" + resultUnit;
    }
}