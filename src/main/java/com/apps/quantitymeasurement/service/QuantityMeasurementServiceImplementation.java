package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.model.QuantityDTO;
import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.model.QuantityModel;
import com.apps.quantitymeasurement.quantity.Quantity;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.unit.IMeasurable;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class QuantityMeasurementServiceImplementation implements IQuantityMeasurementService {

    private static final Logger logger= Logger.getLogger(QuantityMeasurementServiceImplementation.class.getName());

    private final IQuantityMeasurementRepository repository;

    public QuantityMeasurementServiceImplementation(IQuantityMeasurementRepository repository) {
        this.repository= repository;
        logger.info("QuantityMeasurementServiceImplementation initialized");
    }

    @SuppressWarnings("unchecked")
    private <U extends IMeasurable> QuantityModel<U> toModel(QuantityDTO dto) {
        IMeasurable unit= IMeasurable.getUnitByName(dto.getUnit().getUnitName());
        return new QuantityModel<>(dto.getValue(), (U) unit);
    }

    private QuantityDTO fromQuantity(Quantity<?> quantity) {
        IMeasurable unit= quantity.getUnit();
        QuantityDTO.IMeasurableUnit dtoUnit= resolveDtoUnit(unit);
        return new QuantityDTO(quantity.getValue(), dtoUnit);
    }

    private QuantityDTO.IMeasurableUnit resolveDtoUnit(IMeasurable unit) {
        String name= unit.getUnitName();
        String type= unit.getMeasurementType();
        switch (type) {
            case "LENGTH": return QuantityDTO.LengthUnit.valueOf(name);
            case "WEIGHT": return QuantityDTO.WeightUnit.valueOf(name);
            case "VOLUME": return QuantityDTO.VolumeUnit.valueOf(name);
            case "TEMPERATURE": return QuantityDTO.TemperatureUnit.valueOf(name);
            default: throw new QuantityMeasurementException("Unknown measurement type: " + type);
        }
    }

    private void validateSameCategory(QuantityDTO q1, QuantityDTO q2) {
        if (!q1.getUnit().getMeasurementType().equals(q2.getUnit().getMeasurementType()))
            throw new QuantityMeasurementException(
                "Cannot operate on different measurement types: "
                + q1.getUnit().getMeasurementType() + " and " + q2.getUnit().getMeasurementType()
            );
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean compare(Long userId, QuantityDTO q1, QuantityDTO q2) {
        try {
            validateSameCategory(q1, q2);
            QuantityModel model1= toModel(q1);
            QuantityModel model2= toModel(q2);
            boolean result= model1.toQuantity().equals(model2.toQuantity());
            
            if(userId!= null) {
            	repository.save(new QuantityMeasurementEntity(
            			userId,
            			"COMPARE",
            			String.valueOf(q1.getValue()), q1.getUnit().getUnitName(),
            			String.valueOf(q2.getValue()), q2.getUnit().getUnitName(),
            			String.valueOf(result), ""
            	));            	
            }
            
            return result;
        } catch (QuantityMeasurementException e) {
            repository.save(new QuantityMeasurementEntity(
                userId, "COMPARE", String.valueOf(q1.getValue()), q1.getUnit().getUnitName(), e.getMessage()
            ));
            throw e;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO convert(Long userId, QuantityDTO quantity, QuantityDTO.IMeasurableUnit targetUnit) {
        try {
            QuantityModel model= toModel(quantity);
            IMeasurable target= IMeasurable.getUnitByName(targetUnit.getUnitName());
            Quantity converted= model.toQuantity().convertTo(target);
            QuantityDTO result= fromQuantity(converted);
            
            if(userId!=null) {
            	repository.save(new QuantityMeasurementEntity(
            			userId,
            			"CONVERT",
            			String.valueOf(quantity.getValue()), quantity.getUnit().getUnitName(),
            			String.valueOf(result.getValue()), result.getUnit().getUnitName()
            	));
            }
            
            return result;
        } catch (Exception e) {
            repository.save(new QuantityMeasurementEntity(
                userId, "CONVERT", String.valueOf(quantity.getValue()), quantity.getUnit().getUnitName(), e.getMessage()
            ));
            throw new QuantityMeasurementException("Conversion failed: " + e.getMessage(), e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO add(Long userId, QuantityDTO q1, QuantityDTO q2) {
        try {
            validateSameCategory(q1, q2);
            QuantityModel model1= toModel(q1);
            QuantityModel model2= toModel(q2);
            Quantity result= model1.toQuantity().add(model2.toQuantity());
            QuantityDTO dto= fromQuantity(result);
            
            if(userId!=null) {
            	repository.save(new QuantityMeasurementEntity(
            			userId,
            			"ADD",
            			String.valueOf(q1.getValue()), q1.getUnit().getUnitName(),
            			String.valueOf(q2.getValue()), q2.getUnit().getUnitName(),
            			String.valueOf(dto.getValue()), dto.getUnit().getUnitName()
       			));
            	
            }
            return dto;
        } catch (QuantityMeasurementException e) { throw e;
        } catch (Exception e) { throw new QuantityMeasurementException("Addition failed: " + e.getMessage(), e); }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO add(Long userId, QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit) {
        try {
            validateSameCategory(q1, q2);
            QuantityModel model1= toModel(q1);
            QuantityModel model2= toModel(q2);
            IMeasurable target= IMeasurable.getUnitByName(targetUnit.getUnitName());
            Quantity result= model1.toQuantity().add(model2.toQuantity(), target);
            QuantityDTO dto= fromQuantity(result);
            
            if(userId!=null) {
            	repository.save(new QuantityMeasurementEntity(
            			userId,
            			"ADD",
            			String.valueOf(q1.getValue()), q1.getUnit().getUnitName(),
            			String.valueOf(q2.getValue()), q2.getUnit().getUnitName(),
            			String.valueOf(dto.getValue()), dto.getUnit().getUnitName()
        		));            	
            }
            return dto;
        } catch (QuantityMeasurementException e) { throw e;
        } catch (Exception e) { throw new QuantityMeasurementException("Addition failed: " + e.getMessage(), e); }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO subtract(Long userId, QuantityDTO q1, QuantityDTO q2) {
        try {
            validateSameCategory(q1, q2);
            QuantityModel model1= toModel(q1);
            QuantityModel model2= toModel(q2);
            Quantity result= model1.toQuantity().subtract(model2.toQuantity());
            QuantityDTO dto= fromQuantity(result);
            
            if(userId!=null) {
            	repository.save(new QuantityMeasurementEntity(
            			userId,
            			"SUBTRACT",
            			String.valueOf(q1.getValue()), q1.getUnit().getUnitName(),
            			String.valueOf(q2.getValue()), q2.getUnit().getUnitName(),
            			String.valueOf(dto.getValue()), dto.getUnit().getUnitName()
           		));
            }
            
            return dto;
        } catch (QuantityMeasurementException e) { throw e;
        } catch (Exception e) { throw new QuantityMeasurementException("Subtraction failed: " + e.getMessage(), e); }
    }

    @Override
    @SuppressWarnings("unchecked")
    public QuantityDTO subtract(Long userId, QuantityDTO q1, QuantityDTO q2, QuantityDTO.IMeasurableUnit targetUnit) {
        try {
            validateSameCategory(q1, q2);
            QuantityModel model1= toModel(q1);
            QuantityModel model2= toModel(q2);
            IMeasurable target= IMeasurable.getUnitByName(targetUnit.getUnitName());
            Quantity result= model1.toQuantity().subtract(model2.toQuantity(), target);
            QuantityDTO dto= fromQuantity(result);
            
            if(userId!=null) {
            	repository.save(new QuantityMeasurementEntity(
            			userId,
            			"SUBTRACT",
            			String.valueOf(q1.getValue()), q1.getUnit().getUnitName(),
            			String.valueOf(q2.getValue()), q2.getUnit().getUnitName(),
            			String.valueOf(dto.getValue()), dto.getUnit().getUnitName()
            	));
            }
            
            return dto;
        } catch (QuantityMeasurementException e) { throw e;
        } catch (Exception e) { throw new QuantityMeasurementException("Subtraction failed: " + e.getMessage(), e); }
    }

    @Override
    @SuppressWarnings("unchecked")
    public double divide(Long userId, QuantityDTO q1, QuantityDTO q2) {
        try {
            validateSameCategory(q1, q2);
            QuantityModel model1= toModel(q1);
            QuantityModel model2= toModel(q2);
            double result= model1.toQuantity().divide(model2.toQuantity());
            
            if(userId!=null) {
            	repository.save(new QuantityMeasurementEntity(
            			userId,
            			"DIVIDE",
            			String.valueOf(q1.getValue()), q1.getUnit().getUnitName(),
            			String.valueOf(q2.getValue()), q2.getUnit().getUnitName(),
            			String.valueOf(result), "scalar"
            	));            	
            }
            return result;
        } catch (QuantityMeasurementException e) { throw e;
        } catch (Exception e) { throw new QuantityMeasurementException("Division failed: " + e.getMessage(), e); }
    }
}