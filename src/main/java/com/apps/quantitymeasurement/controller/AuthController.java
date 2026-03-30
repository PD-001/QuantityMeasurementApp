package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.repository.UserRepository;
import com.apps.quantitymeasurement.security.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    public AuthController(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository= userRepository;
        this.jwtTokenProvider= jwtTokenProvider;
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
}