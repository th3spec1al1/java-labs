package ru.kurbanov.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.kurbanov.application.services.CarGrpcClient;
import ru.kurbanov.proto.storage.CarsProto;
import ru.kurbanov.proto.storage.CarServiceGrpc;
import ru.kurbanov.domain.exceptions.StorageServiceUnavailableException;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class CarGrpcClientTest {

    private static final String CAR_ID = "a1111111-1111-1111-1111-111111111111";
    private static final String MODEL_ID = "b1111111-1111-1111-1111-111111111111";

    private Server server;
    private ManagedChannel channel;
    private CarGrpcClient client;

    @BeforeEach
    void setUp() throws IOException {
        server = ServerBuilder.forPort(0)
                .addService(new TestCarServiceImpl())
                .build()
                .start();
        channel = ManagedChannelBuilder.forAddress("localhost", server.getPort())
                .usePlaintext()
                .build();
        client = new CarGrpcClient(CarServiceGrpc.newBlockingStub(channel));
    }

    @AfterEach
    void tearDown() {
        channel.shutdownNow();
        server.shutdownNow();
    }

    @Test
    void listAvailableCarsShouldReturnCars() {
        List<CarsProto.Car> cars = client.listAvailableCars();

        assertEquals(1, cars.size());
        assertEquals(CAR_ID, cars.get(0).getId());
        assertEquals("VIN001", cars.get(0).getVin());
        assertEquals("AVAILABLE", cars.get(0).getStatus());
    }

    @Test
    void getCarByIdShouldReturnCar() {
        Optional<CarsProto.Car> car = client.getCarById(CAR_ID);

        assertTrue(car.isPresent());
        assertEquals(CAR_ID, car.get().getId());
        assertEquals("VIN001", car.get().getVin());
        assertEquals("AVAILABLE", car.get().getStatus());
    }

    @Test
    void getCarByIdShouldReturnEmpty() {
        Optional<CarsProto.Car> car = client.getCarById("00000000-0000-0000-0000-000000000000");

        assertTrue(car.isEmpty());
    }

    @Test
    void listAvailableCarsShouldReturn503WhenServerUnavailable() {
        channel.shutdownNow();
        server.shutdownNow();

        assertThrows(StorageServiceUnavailableException.class, () -> client.listAvailableCars());
    }

    private static class TestCarServiceImpl extends CarServiceGrpc.CarServiceImplBase {

        @Override
        public void listAvailableCars(CarsProto.Empty request,
                                      StreamObserver<CarsProto.CarListResponse> responseObserver) {
            responseObserver.onNext(CarsProto.CarListResponse.newBuilder().addCars(testCar()).build());
            responseObserver.onCompleted();
        }

        @Override
        public void getCarById(CarsProto.CarRequest request,
                               StreamObserver<CarsProto.Car> responseObserver) {
            if (!CAR_ID.equals(request.getId())) {
                responseObserver.onError(Status.NOT_FOUND.withDescription("Car not found").asRuntimeException());
                return;
            }
            responseObserver.onNext(testCar());
            responseObserver.onCompleted();
        }

        private static CarsProto.Car testCar() {
            return CarsProto.Car.newBuilder()
                    .setId(CAR_ID)
                    .setModelId(MODEL_ID)
                    .setVin("VIN001")
                    .setStatus("AVAILABLE")
                    .build();
        }
    }
}
