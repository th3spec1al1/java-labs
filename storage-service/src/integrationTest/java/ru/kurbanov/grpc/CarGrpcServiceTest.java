package ru.kurbanov.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.Server;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.kurbanov.TestStorageServiceApplication;
import ru.kurbanov.proto.storage.CarsProto;
import ru.kurbanov.proto.storage.CarServiceGrpc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CarGrpcServiceTest extends TestStorageServiceApplication {

    private static final UUID AVAILABLE_CAR_ID = UUID.fromString("a1111111-1111-1111-1111-111111111111");
    private static final UUID MODEL_ID = UUID.fromString("b1111111-1111-1111-1111-111111111111");
    private static final UUID RESERVED_CAR_ID = UUID.fromString("a9999999-9999-9999-9999-999999999999");

    @Autowired
    private Server grpcServer;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ManagedChannel channel;
    private CarServiceGrpc.CarServiceBlockingStub stub;

    @BeforeEach
    void setUp() {
        channel = ManagedChannelBuilder.forAddress("localhost", grpcServer.getPort())
                .usePlaintext()
                .build();
        stub = CarServiceGrpc.newBlockingStub(channel);

        jdbcTemplate.update(
                """
                INSERT INTO storage_cars(id, model_id, vin, status, created_at, updated_at, removed)
                VALUES(?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO UPDATE SET status = excluded.status
                """,
                AVAILABLE_CAR_ID,
                MODEL_ID,
                "VIN001",
                "AVAILABLE",
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                false
        );
    }

    @AfterEach
    void tearDown() {
        channel.shutdownNow();
        jdbcTemplate.update("DELETE FROM storage_cars WHERE id = ?", AVAILABLE_CAR_ID);
        jdbcTemplate.update("DELETE FROM storage_cars WHERE id = ?", RESERVED_CAR_ID);
    }

    @Test
    void listAvailableCarsShouldReturnOnlyAvailableCars() {
        jdbcTemplate.update(
                """
                INSERT INTO storage_cars(id, model_id, vin, status, created_at, updated_at, removed)
                VALUES(?, ?, ?, ?, ?, ?, ?)
                ON CONFLICT (id) DO NOTHING
                """,
                RESERVED_CAR_ID,
                MODEL_ID,
                "VIN999",
                "RESERVED",
                Timestamp.from(Instant.now()),
                Timestamp.from(Instant.now()),
                false
        );

        CarsProto.CarListResponse response = stub.listAvailableCars(CarsProto.Empty.newBuilder().build());

        assertEquals(1, response.getCarsCount());
        assertEquals(AVAILABLE_CAR_ID.toString(), response.getCars(0).getId());
        assertEquals("AVAILABLE", response.getCars(0).getStatus());
    }

    @Test
    void getCarByIdShouldReturnCar() {
        CarsProto.Car car = stub.getCarById(
                CarsProto.CarRequest.newBuilder()
                        .setId(AVAILABLE_CAR_ID.toString())
                        .build());

        assertEquals(AVAILABLE_CAR_ID.toString(), car.getId());
        assertEquals("VIN001", car.getVin());
        assertEquals("AVAILABLE", car.getStatus());
    }

    @Test
    void getCarByIdShouldReturnNotFound() {
        StatusRuntimeException ex = assertThrows(StatusRuntimeException.class, () ->
                stub.getCarById(CarsProto.CarRequest.newBuilder()
                        .setId("00000000-0000-0000-0000-000000000000")
                        .build()));

        assertEquals(Status.Code.NOT_FOUND, ex.getStatus().getCode());
    }
}
