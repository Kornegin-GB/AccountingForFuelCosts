package ru.fsv67.dto.car;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Сущность автомобиль")
public class Car {
    @Schema(description = "Идентификатор автомобиля")
    private Long id;

    @Schema(description = "Регистрационный номер автомобиля")
    private String registrationNumber;

    @Schema(description = "Уникальный VIN номер автомобиля")
    private String vin;

    @Schema(description = "Норма расхода топлива автомобиля")
    private Double fuelConsumptionRate;

    @Schema(description = "Серия для маршрутного листа")
    private Integer seriesItinerarySheet;

    @Schema(description = "Начальные показания одометра автомобиля")
    private Integer initialOdometer;

    @Schema(description = "Модель автомобиля")
    private Model carModel;
}
