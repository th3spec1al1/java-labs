package ru.kurbanov.presentation.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kurbanov.application.services.HealthService;
import ru.kurbanov.domain.health.HealthState;

@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthController {

    private final HealthService healthService;

    @GetMapping
    public ResponseEntity<HealthState> health() {
        HealthState state = healthService.getHealth();
        return state.status().equals("UP")
                ? ResponseEntity.ok(state)
                : ResponseEntity.status(503).body(state);
    }
}