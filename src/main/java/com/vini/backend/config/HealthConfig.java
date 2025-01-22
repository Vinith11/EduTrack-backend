package com.vini.backend.config;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/health")
public class HealthConfig {

    @GetMapping
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("response");
    }
}
