package ru.kurbanov.application.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kurbanov.domain.health.HealthState;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class HealthService {

    private final DataSource dataSource;

    public HealthState getHealth() {
        String dbStatus;
        try (Connection connection = dataSource.getConnection()) {
            dbStatus = connection.isValid(2) ? "UP" : "DOWN";
        } catch (Exception e) {
            dbStatus = "DOWN";
        }

        return new HealthState(dbStatus.equals("UP") ? "UP" : "DOWN", dbStatus);
    }
}