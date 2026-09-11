package ru.kurbanov.application.services;

import io.grpc.ManagedChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kurbanov.domain.health.HealthState;

import javax.sql.DataSource;
import java.sql.Connection;

@Service
@RequiredArgsConstructor
public class HealthService {

    private final DataSource dataSource;
    private final ManagedChannel storageServiceChannel;

    public HealthState getHealth() {
        storageServiceChannel.getState(true);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        String grpcStatus = storageServiceChannel.getState(false).name();

        String dbStatus;
        try (Connection connection = dataSource.getConnection()) {
            dbStatus = connection.isValid(2) ? "UP" : "DOWN";
        } catch (Exception e) {
            dbStatus = "DOWN";
        }

        String overall = (dbStatus.equals("UP") && grpcStatus.equals("READY")) ? "UP" : "DOWN";
        return new HealthState(overall, dbStatus, grpcStatus);
    }
}
