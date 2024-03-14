package ru.fsv67;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Микро сервис Управления автомобилями
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Учет ГСМ на автомобиле - управление автомобилями",
                description = "Микро сервис управление автомобилями",
                version = "1.0.0"
        )
)
@SpringBootApplication
public class CarsMicroService {
    public static void main(String[] args) {
        SpringApplication.run(CarsMicroService.class, args);
    }
}