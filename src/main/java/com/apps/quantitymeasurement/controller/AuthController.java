package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.model.UserEntity;
import com.apps.quantitymeasurement.repository.UserRepository;
import com.apps.quantitymeasurement.security.JwtTokenProvider;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.userRepository= userRepository;
        this.jwtTokenProvider= jwtTokenProvider;
        this.passwordEncoder= passwordEncoder;
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentUser(Principal principal) {

        String email= principal.getName();

        return userRepository.findByEmail(email)
                .map(user -> {
                    Map<String, Object> body= new HashMap<>();
                    body.put("id",user.getId());
                    body.put("email",user.getEmail());
                    body.put("name",user.getName());
                    body.put("picture",user.getPicture() != null ? user.getPicture() : "");
                    body.put("provider", user.getProvider());
                    return ResponseEntity.ok(body);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, String>> status() {
        return ResponseEntity.ok(Map.of(
            "status","running",
            "message","Visit /oauth2/authorization/google to log in"
        ));
    }
    

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody Map<String, String> body) {
        String email= body.get("email");
        String password= body.get("password");
        String name= body.getOrDefault("name", email);

        if (email==null || password==null || email.isBlank() || password.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email and password are required"));
        }

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Email already registered"));
        }

        String hash= passwordEncoder.encode(password);
        UserEntity user= userRepository.save(new UserEntity(email, name, hash));

        String token= jwtTokenProvider.generateToken(email, user.getName(), user.getId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("token", token));
    }
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> body) {
        String email= body.get("email");
        String password= body.get("password");

        if (email==null || password==null) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email and password are required"));
        }

        return userRepository.findByEmail(email)
                .map(user->{
                    if (!"local".equals(user.getProvider())) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .<Map<String, Object>>body(
                                    Map.of("error", "This account uses Google login"));
                    }

                    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .<Map<String, Object>>body(
                                    Map.of("error", "Invalid credentials"));
                    }

                    String token= jwtTokenProvider.generateToken(
                                        email, user.getName(), user.getId());
                    return ResponseEntity.ok(
                                Map.<String, Object>of("token", token));
                })
                .orElseGet(()->ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials")));
    }
}