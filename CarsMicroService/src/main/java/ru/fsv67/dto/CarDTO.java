package ru.fsv67.dto;

import lombok.Data;

@Data
public class CarDTO {
    private Long id;
    private String registrationNumber;
    private String vin;
    private ModelDTO carModel;
    private Double fuelConsumptionRate;
    private Integer seriesItinerarySheet;
    private Integer initialOdometer;
}
