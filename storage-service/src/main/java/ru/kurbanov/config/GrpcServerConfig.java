package ru.kurbanov.config;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kurbanov.presentation.grpc.CarGrpcService;

@Configuration
public class GrpcServerConfig {

    @Bean(initMethod = "start", destroyMethod = "shutdown")
    public Server grpcServer(
        CarGrpcService carGrpcService,
        @Value("${grpc.server.port:9090}") int port
    ) {
        return ServerBuilder
                .forPort(port)
                .addService(carGrpcService)
                .build();
    }
}
