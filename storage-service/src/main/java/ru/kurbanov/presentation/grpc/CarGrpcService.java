package ru.kurbanov.presentation.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kurbanov.domain.entities.CarStatus;
import ru.kurbanov.infrastructure.persistence.jpa.JpaCarRepository;
import ru.kurbanov.infrastructure.persistence.jpa.model.CarEntity;
import ru.kurbanov.proto.storage.CarServiceGrpc;
import ru.kurbanov.proto.storage.CarsProto;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarGrpcService extends CarServiceGrpc.CarServiceImplBase {

    private final JpaCarRepository carRepository;

    @Override
    public void listAvailableCars(CarsProto.Empty request,
                                  StreamObserver<CarsProto.CarListResponse> responseObserver) {
        log.info("grpc request: listAvailableCars");
        try {
            List<CarEntity> cars = carRepository.findByStatusAndRemovedFalse(CarStatus.AVAILABLE);
            List<CarsProto.Car> protoCars = cars.stream()
                    .map(this::toProtoCar)
                    .toList();
            CarsProto.CarListResponse response = CarsProto.CarListResponse.newBuilder()
                    .addAllCars(protoCars)
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            log.info("grpc response: listAvailableCars returned {} cars", protoCars);
        } catch (Exception e) {
            log.error("grpc error: listAvailableCars", e);
            responseObserver.onError(e);
        }
    }

    @Override
    public void getCarById(CarsProto.CarRequest request,
                           StreamObserver<CarsProto.Car> responseObserver) {
        log.info("grpc request: getCarById id={}", request.getId());
        try {
            carRepository.findByIdAndRemovedFalse(java.util.UUID.fromString(request.getId()))
                    .map(this::toProtoCar)
                    .ifPresentOrElse(
                            car -> {
                                responseObserver.onNext(car);
                                responseObserver.onCompleted();
                                log.info("grpc response: getCarById found car id={}", request.getId());
                            },
                            () -> {
                                responseObserver.onError(
                                        io.grpc.Status.NOT_FOUND
                                                .withDescription("Car not found: " + request.getId())
                                                .asRuntimeException()
                                );
                            }
                    );
        } catch (Exception e) {
            log.error("grpc error: getCarById", e);
            responseObserver.onError(e);
        }
    }

    private CarsProto.Car toProtoCar(CarEntity entity) {
        return CarsProto.Car.newBuilder()
                .setId(entity.getId().toString())
                .setModelId(entity.getModelId().toString())
                .setVin(entity.getVin())
                .setStatus(entity.getStatus().name())
                .build();
    }
}
