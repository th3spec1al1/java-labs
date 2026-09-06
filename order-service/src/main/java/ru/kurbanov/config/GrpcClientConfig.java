package ru.kurbanov.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kurbanov.proto.storage.CarServiceGrpc;

@Configuration
public class GrpcClientConfig {

    @Value("${grpc.client.storage-service.host:localhost}")
    private String storageServiceHost;

    @Value("${grpc.client.storage-service.port:9090}")
    private int storageServicePort;

    @Bean
    public ManagedChannel storageServiceChannel() {
        return ManagedChannelBuilder
                .forAddress(storageServiceHost, storageServicePort)
                .usePlaintext()
                .build();
    }

    @Bean
    public CarServiceGrpc.CarServiceBlockingStub carServiceStub(ManagedChannel storageServiceChannel) {
        return CarServiceGrpc.newBlockingStub(storageServiceChannel);
    }
}
