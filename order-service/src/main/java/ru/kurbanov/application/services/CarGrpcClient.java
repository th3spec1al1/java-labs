package ru.kurbanov.application.services;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import ru.kurbanov.domain.exceptions.StorageServiceUnavailableException;
import ru.kurbanov.proto.storage.CarServiceGrpc;
import ru.kurbanov.proto.storage.CarsProto;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CarGrpcClient {

    @GrpcClient("storage-service")
    private CarServiceGrpc.CarServiceBlockingStub carServiceStub;

    public List<CarsProto.Car> listAvailableCars() {
        log.info("grpc call: listAvailableCars");
        try {
            CarsProto.CarListResponse response =
                    carServiceStub.listAvailableCars(CarsProto.Empty.newBuilder().build());
            log.info("grpc response: {} cars received", response.getCarsList().size());
            return response.getCarsList();
        } catch (StatusRuntimeException e) {
            log.error("grpc error: listAvailableCars - {}", e.getStatus());
            throw new StorageServiceUnavailableException(
                    "Storage service unavailable: " + e.getStatus().getDescription());
        }
    }

    public Optional<CarsProto.Car> getCarById(String id) {
        log.info("grpc call: getCarById id={}", id);
        try {
            CarsProto.Car car = carServiceStub.getCarById(
                    CarsProto.CarRequest.newBuilder().setId(id).build());
            log.info("grpc response: car found id={}", id);
            return Optional.of(car);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                return Optional.empty();
            }
            log.error("grpc error: getCarById - {}", e.getStatus());
            throw new StorageServiceUnavailableException(
                    "Storage service unavailable: " + e.getStatus().getDescription());
        }
    }
}
