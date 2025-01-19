package com.orders.gateway.config;

import com.orders.gateway.OrderServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Configuration
public class GrpcClientConfig {

    @Value("${grpc.server.host:domain}")
    private String host;

    @Value("${grpc.server.port:9090}")
    private int port;

    private ManagedChannel channel;

    @Bean
    public OrderServiceGrpc.OrderServiceBlockingStub orderServiceBlockingStub() {
        return OrderServiceGrpc.newBlockingStub(managedChannel());
    }

    @Bean
    public ManagedChannel managedChannel() {
        if (channel == null) {
            channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .build();
        }
        return channel;
    }

    @PreDestroy
    public void shutdown() throws InterruptedException {
        if (channel != null) {
            channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
        }
    }
}
