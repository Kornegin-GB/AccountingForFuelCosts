package ru.fsv67.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Сущность идентификационные данные автомобиля
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
@Schema(description = "Сущность автомобиль")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор автомобиля")
    private Long id;

    @Column(name = "registration_number")
    @Schema(description = "Регистрационный номер автомобиля")
    private String registrationNumber;

    @Column(name = "vin")
    @Schema(description = "VIN номер автомобиля")
    private String vin;

    @Column(name = "car_model_id")
    @Schema(description = "Идентификатор модели автомобиля")
    private Long carModel;

    @Column(name = "fuel_consumption_rate")
    @Schema(description = "Норма расхода топлива")
    private Double fuelConsumptionRate;

    @Column(name = "series_itinerary_sheet")
    @Schema(description = "Серия для маршрутного листа")
    private Integer seriesItinerarySheet;

    @Column(name = "initial_odometer")
    @Schema(description = "Начальные показания одометра")
    private Integer initialOdometer;

    public CarEntity(String registrationNumber, String vin, Long carModel, Double fuelConsumptionRate, Integer seriesItinerarySheet, Integer initialOdometer) {
        this.registrationNumber = registrationNumber;
        this.vin = vin;
        this.carModel = carModel;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.seriesItinerarySheet = seriesItinerarySheet;
        this.initialOdometer = initialOdometer;
    }
}
