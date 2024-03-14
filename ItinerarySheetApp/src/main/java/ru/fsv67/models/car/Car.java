package ru.fsv67.models.car;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private Long id;
    private String registrationNumber;
    private String vin;
    private Double fuelConsumptionRate;
    private Integer seriesItinerarySheet;
    private Integer initialOdometer;
    private Model carModel;

    public Car(String registrationNumber, String vin, Double fuelConsumptionRate, Integer seriesItinerarySheet, Integer initialOdometer, Model carModel) {
        this.registrationNumber = registrationNumber;
        this.vin = vin;
        this.fuelConsumptionRate = fuelConsumptionRate;
        this.seriesItinerarySheet = seriesItinerarySheet;
        this.initialOdometer = initialOdometer;
        this.carModel = carModel;
    }
}
