package ru.fsv67.models.fuel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fuel_brands")
@Schema(description = "Сущность марка топлива")
public class FuelBrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Идентификатор марки топлива")
    @Column(name = "fuel_brand_id")
    private Long id;
    @Schema(description = "Марка топлива")
    @Column(name = "fuel_brand_name")
    private String fuelBrandName;

    public FuelBrandEntity(String fuelBrandName) {
        this.fuelBrandName = fuelBrandName;
    }
}
