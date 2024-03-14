package ru.fsv67;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Микро сервис управления водителями
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Учет ГСМ на автомобиле - управление водителями",
                description = "Микро сервис управление водителями",
                version = "1.0.0"
        )
)
@SpringBootApplication
public class DriversMicroService {
    public static void main(String[] args) {
        SpringApplication.run(DriversMicroService.class, args);
    }
}