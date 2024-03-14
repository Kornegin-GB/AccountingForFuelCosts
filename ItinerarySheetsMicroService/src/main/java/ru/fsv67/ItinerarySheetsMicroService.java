package ru.fsv67;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Микро сервис управления движением ГСМ на автомобиле
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Учет ГСМ на автомобиле - управление маршрутными листами",
                description = "Микро сервис управление маршрутными листами",
                version = "1.0.0"
        )
)
@SpringBootApplication
public class ItinerarySheetsMicroService {
    public static void main(String[] args) {
        SpringApplication.run(ItinerarySheetsMicroService.class, args);
    }
}