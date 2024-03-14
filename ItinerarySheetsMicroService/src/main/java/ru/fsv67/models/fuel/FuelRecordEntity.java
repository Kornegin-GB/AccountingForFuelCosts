package ru.fsv67.models.fuel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fuel_records")
@Schema(description = "Сущность заправка автомобиля")
public class FuelRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор заправки автомобиля")
    private Long id;

    @Column(name = "refueling_date")
    @Schema(description = "Дата заправки автомобиля")
    private LocalDate refuelingDate;

    @ManyToOne
    @JoinColumn(name = "fuel_brand_id")
    @Schema(description = "Марка заправленного топлива")
    private FuelBrandEntity fuelBrand;

    @Column(name = "fuel_quantity")
    @Schema(description = "Количество заправленного топлива")
    private Double fuelQuantity;

    @Column(name = "fuel_price")
    @Schema(description = "Цена за 1 литр заправленного топлива")
    private Double fuelPrice;

    @Column(name = "fuel_sum")
    @Schema(description = "Сумма всего заправленного топлива")
    private Double fuelSum;

    public FuelRecordEntity(LocalDate refuelingDate, FuelBrandEntity fuelBrand, Double fuelQuantity, Double fuelPrice, Double fuelSum) {
        this.refuelingDate = refuelingDate;
        this.fuelBrand = fuelBrand;
        this.fuelQuantity = fuelQuantity;
        this.fuelPrice = fuelPrice;
        this.fuelSum = fuelSum;
    }
}
