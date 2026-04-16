package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.model.QuantityDTO;
import com.apps.quantitymeasurement.model.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.repository.UserRepository;
import com.apps.quantitymeasurement.security.JwtTokenProvider;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/measurements")
public class QuantityMeasurementController {

    private final IQuantityMeasurementService service;
    private final IQuantityMeasurementRepository repository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // Spring injects both automatically
    public QuantityMeasurementController(IQuantityMeasurementService service,
                                          IQuantityMeasurementRepository repository,
                                          JwtTokenProvider jwtTokenProvider,
                                          UserRepository userRepository) {
        this.service= service;
        this.repository= repository;
        this.jwtTokenProvider= jwtTokenProvider;
		this.userRepository = userRepository;
    }
    
    private Long getCurrentUserId(HttpServletRequest request) {
        String header= request.getHeader("Authorization");
        if (header!= null && header.startsWith("Bearer ")) {
            try {
                String token= header.substring(7);
                return jwtTokenProvider.getUserIdFromToken(token);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    // GET /api/measurements
    @GetMapping
    public ResponseEntity<List<QuantityMeasurementEntity>> getAll(HttpServletRequest request) {
    	Long userId = getCurrentUserId(request);
        return ResponseEntity.ok(
            repository.findByUserIdOrderByCreatedAtDesc(userId)
        );
    }

    // GET /api/measurements/count
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getCount(HttpServletRequest request) {
    	Long userId = getCurrentUserId(request);
        return ResponseEntity.ok(Map.of("count", repository.countByUserId(userId)));
    }

    // DELETE /api/measurements
    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteAll(HttpServletRequest request) {
    	Long userId = getCurrentUserId(request);
        repository.deleteByUserId(userId);
        return ResponseEntity.ok(Map.of("message", "Your measurements deleted"));
    }

    // POST /api/measurements/compare
    // Body: { "q1": {"value":1.0,"unit":"FEET"}, "q2": {"value":12.0,"unit":"INCHES"} }
    @PostMapping("/compare")
    public ResponseEntity<Map<String, Object>> compare(@RequestBody TwoQuantityRequest req, HttpServletRequest request) {
    	Long userId = getCurrentUserId(request);
    	boolean result= service.compare(userId, req.getQ1(), req.getQ2());
        return ResponseEntity.ok(Map.of(
            "operation", "COMPARE",
            "q1", req.getQ1().toString(),
            "q2", req.getQ2().toString(),
            "result", result
        ));
    }

    // POST /api/measurements/convert
    // Body: { "quantity": {"value":1.0,"unit":"FEET"}, "targetUnit": "INCHES" }
    @PostMapping("/convert")
    public ResponseEntity<Map<String, Object>> convert(@RequestBody ConversionRequest req, HttpServletRequest request) {
        Long userId= getCurrentUserId(request);
    	QuantityDTO result= service.convert(userId, req.getQuantity(), req.resolveTargetUnit());
        return ResponseEntity.ok(Map.of(
            "operation", "CONVERT",
            "input", req.getQuantity().toString(),
            "targetUnit", req.getTargetUnit(),
            "result", result.toString()
        ));
    }

    // POST /api/measurements/add
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> add(@RequestBody TwoQuantityRequest req, HttpServletRequest request) {
    	Long userId = getCurrentUserId(request);
    	QuantityDTO result= service.add(userId, req.getQ1(), req.getQ2());
        return ResponseEntity.ok(Map.of(
            "operation", "ADD",
            "q1", req.getQ1().toString(),
            "q2", req.getQ2().toString(),
            "result", result.toString()
        ));
    }

    // POST /api/measurements/subtract
    @PostMapping("/subtract")
    public ResponseEntity<Map<String, Object>> subtract(@RequestBody TwoQuantityRequest req, HttpServletRequest request) {
    	Long userId = getCurrentUserId(request);
    	QuantityDTO result= service.subtract(userId, req.getQ1(), req.getQ2());
        return ResponseEntity.ok(Map.of(
            "operation", "SUBTRACT",
            "q1", req.getQ1().toString(),
            "q2", req.getQ2().toString(),
            "result", result.toString()
        ));
    }

    // POST /api/measurements/divide
    @PostMapping("/divide")
    public ResponseEntity<Map<String, Object>> divide(@RequestBody TwoQuantityRequest req, HttpServletRequest request) {
    	Long userId = getCurrentUserId(request);
    	double result= service.divide(userId, req.getQ1(), req.getQ2());
        return ResponseEntity.ok(Map.of(
            "operation", "DIVIDE",
            "q1", req.getQ1().toString(),
            "q2", req.getQ2().toString(),
            "result", result
        ));
    }

    // Inner request classes

    public static class TwoQuantityRequest {
        private QuantityDTO q1;
        private QuantityDTO q2;
        public QuantityDTO getQ1() { return q1; }
        public QuantityDTO getQ2() { return q2; }
        public void setQ1(QuantityDTO q1) { this.q1= q1; }
        public void setQ2(QuantityDTO q2) { this.q2= q2; }
    }

    public static class ConversionRequest {
        private QuantityDTO quantity;
        private String targetUnit;

        public QuantityDTO getQuantity() { return quantity; }
        public String getTargetUnit()    { return targetUnit; }
        public void setQuantity(QuantityDTO quantity) { this.quantity = quantity; }
        public void setTargetUnit(String targetUnit)  { this.targetUnit = targetUnit; }

        public QuantityDTO.IMeasurableUnit resolveTargetUnit() {
            String type= quantity.getUnit().getMeasurementType();
            switch (type) {
                case "LENGTH": return QuantityDTO.LengthUnit.valueOf(targetUnit);
                case "WEIGHT": return QuantityDTO.WeightUnit.valueOf(targetUnit);
                case "VOLUME": return QuantityDTO.VolumeUnit.valueOf(targetUnit);
                case "TEMPERATURE": return QuantityDTO.TemperatureUnit.valueOf(targetUnit);
                default: throw new RuntimeException("Unknown measurement type: " + type);
            }
        }
    }
}