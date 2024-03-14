package ru.fsv67.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import ru.fsv67.dto.car.Car;
import ru.fsv67.dto.driver.Driver;
import ru.fsv67.models.AddressEntity;
import ru.fsv67.models.fuel.FuelRecordEntity;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Schema(description = "Маршрутный лист")
public class ItinerarySheetDTO {
    @Schema(description = "Идентификатор маршрутного листа")
    private Long id;

    @Schema(description = "Номер маршрутного листа")
    private Integer documentNumber;

    @Schema(description = "Серия маршрутного листа")
    private Integer documentSeries;

    @Schema(description = "Дата выезда")
    private LocalDateTime departureDate;

    @Schema(description = "Дата возвращения")
    private LocalDateTime returnDate;

    @Schema(description = "Автомобиль")
    private Car car;

    @Schema(description = "Показание одометра при выезде")
    private Integer exitOdometer;

    @Schema(description = "показание одометра при возвращении")
    private Integer returnOdometer;

    @Schema(description = "Водитель")
    private Driver driver;

    @Schema(description = "Остаток топлива при выезде")
    private Double initialBalance;

    @Schema(description = "израсходовано топлива за поездку")
    private Double fuelConsumption;

    @Schema(description = "Остаток топлива при возвращении")
    private Double finalBalance;

    @Schema(description = "Список заправок автомобиля")
    private List<FuelRecordEntity> fuelRecords;

    @Schema(description = "Список адресов поездки автомобиля")
    private List<AddressEntity> address;
}
