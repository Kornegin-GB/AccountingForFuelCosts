package ru.fsv67.models.itinerarySheet;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Data
public class FuelRecord {
    private Long id;
    private LocalDate refuelingDate;
    private FuelBrand fuelBrand;
    private Double fuelQuantity;
    private Double fuelPrice;
    private Double fuelSum;
    private Long itinerarySheetId;

    public FuelRecord(LocalDate refuelingDate, FuelBrand fuelBrand, Double fuelQuantity, Double fuelPrice, Double fuelSum) {
        this.refuelingDate = refuelingDate;
        this.fuelBrand = fuelBrand;
        this.fuelQuantity = fuelQuantity;
        this.fuelPrice = fuelPrice;
        this.fuelSum = fuelSum;
    }
}
