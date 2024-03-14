package ru.fsv67.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.fsv67.models.fuel.FuelRecordEntity;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItinerarySheetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Идентификатор документа

    @Column(name = "number")
    private Integer documentNumber; //Номер документа

    @Column(name = "series")
    private Integer documentSeries; //Серия документа

    @Column(name = "departure_date")
    private LocalDateTime departureDate; //Дата выезда

    @Column(name = "return_date")
    private LocalDateTime returnDate; // Дата возвращения

    @Column(name = "car_id")
    private Long carId; //Автомобиль

    @Column(name = "exit_odometer")
    private Integer exitOdometer; //Показание одометра при выезде

    @Column(name = "return_odometer")
    private Integer returnOdometer; // Показание одометра при возвращении

    @Column(name = "driver_id")
    private Long driverId; // Водитель

    @Column(name = "initial_balance")
    private Double initialBalance; //Остаток топлива при выезде

    @Column(name = "fuel_consumption")
    private Double fuelConsumption; // Расход топлива

    @Column(name = "final_balance")
    private Double finalBalance; //Остаток топлива при возвращении

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "itinerary_sheet_id")
    private List<FuelRecordEntity> fuelRecords; // Список заправок

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "address")
    private List<AddressEntity> address; // Список адресов маршрута

    public ItinerarySheetEntity(Integer documentNumber, Integer documentSeries, LocalDateTime departureDate,
                                LocalDateTime returnDate, Long carId, Integer exitOdometer, Integer returnOdometer,
                                Long driverId, Double initialBalance, Double fuelConsumption, Double finalBalance,
                                List<AddressEntity> address) {
        this.documentNumber = documentNumber;
        this.documentSeries = documentSeries;
        this.departureDate = departureDate;
        this.returnDate = returnDate;
        this.carId = carId;
        this.exitOdometer = exitOdometer;
        this.returnOdometer = returnOdometer;
        this.driverId = driverId;
        this.initialBalance = initialBalance;
        this.fuelConsumption = fuelConsumption;
        this.finalBalance = finalBalance;
        this.address = address;
    }
}
