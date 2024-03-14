package ru.fsv67;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Микро сервис регистрации микросервисов в системе
 */
@EnableEurekaServer
@SpringBootApplication
public class DiscoveryMicroService {
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryMicroService.class, args);
    }
}