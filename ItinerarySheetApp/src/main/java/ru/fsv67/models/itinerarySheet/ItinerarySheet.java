package ru.fsv67.models.itinerarySheet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.fsv67.models.car.Car;
import ru.fsv67.models.driver.Driver;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItinerarySheet {
    private Long id;
    private Integer documentNumber;
    private Integer documentSeries;
    private LocalDateTime departureDate;
    private LocalDateTime returnDate;
    private Car car;
    private Integer exitOdometer;
    private Integer returnOdometer;
    private Driver driver;
    private Double initialBalance;
    private Double fuelConsumption;
    private Double finalBalance;
    private List<FuelRecord> fuelRecords;
    private List<Address> address;

    public ItinerarySheet(Integer documentNumber, Integer documentSeries, LocalDateTime departureDate, LocalDateTime returnDate, Car car, Integer exitOdometer, Integer returnOdometer, Driver driver, Double initialBalance, Double fuelConsumption, Double finalBalance, List<FuelRecord> fuelRecords, List<Address> address) {
        this.documentNumber = documentNumber;
        this.documentSeries = documentSeries;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.car = car;
        this.exitOdometer = exitOdometer;
        this.returnOdometer = returnOdometer;
        this.driver = driver;
        this.initialBalance = initialBalance;
        this.fuelConsumption = fuelConsumption;
        this.finalBalance = finalBalance;
        this.fuelRecords = fuelRecords;
        this.address = address;
    }
}
